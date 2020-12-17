package com.taiyilin.mandarinlearning.sentenceReordering

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.taiyilin.mandarinlearning.MandarinLearningApplication
import com.taiyilin.mandarinlearning.data.*
import com.taiyilin.mandarinlearning.data.source.MandarinLearningRepository
import com.taiyilin.mandarinlearning.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SentenceReorderingViewModel(
    private val repository: MandarinLearningRepository,
    private val classroomArgs: Classroom?
) : ViewModel() {

    //Classroom Data argument from Classroom Fragment
    private val _classroomData = MutableLiveData<Classroom>().apply {
        value = classroomArgs
    }
    val classroomData: LiveData<Classroom>
        get() = _classroomData


    //Course Data
    private var courseData: Course? = null


    //Question Data
    private var questionList = mutableListOf<Question>()

    private val _questionData = MutableLiveData<Question>()
    val questionData: LiveData<Question>
        get() = _questionData

    private var position = 0

    //For blocking clicking next question
    private val _showToast = MutableLiveData<Int>()
    val showToast: LiveData<Int>
        get() = _showToast


    //ChatRoom
    private val _message = MutableLiveData<Message>()
    val message: LiveData<Message>
        get() = _message

    var liveMessage = MutableLiveData<List<Message>>()


    //Set Answer
    val answerString = MutableLiveData<String>()

    //Answer Output
    private val _answerOutput =MutableLiveData<String>()
    val answerOutput : LiveData<String>
        get() = _answerOutput


    // status: The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    // error: The internal MutableLiveData that stores the error of the most recent request
    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error

    // status for the loading icon of swl
    private val _refreshStatus = MutableLiveData<Boolean>()

    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * When the [ViewModel] is finished, we cancel our coroutine [viewModelJob], which tells the
     * Retrofit service to stop.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


    init {
        _classroomData.value = classroomArgs
//        getCourseData("C01")
        getQuestionsResult()
        getAllLiveMessages()
        sendAnswer()
//        getAnswerOutput()
    }


    private fun getQuestionsResult() {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getQuestions(classroomArgs!!)

            when (result) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    questionList.addAll(result.data)
                    Log.d("Question", "Questions=${result.data}")
                    _questionData.value = questionList[0]

                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR

                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR

                }
                else -> {
                    _error.value = MandarinLearningApplication.instance.toString()
                    _status.value = LoadApiStatus.ERROR

                }
            }
            _refreshStatus.value = false
        }
    }


    // 取得所有監聽的的Live課程
    private fun getAllLiveMessages() {
        liveMessage = repository.getAllLiveMessages(classroomArgs!!)
        _status.value = LoadApiStatus.DONE
        _refreshStatus.value = false
    }


    // 輸入訊息
    fun sendAnswer() {
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val answer = Answer()
            answer.answer = answerString.value ?: ""  //?:如果前面值為null 就給後面的""
            answer.questionNumber = questionData.value?.number

            val result = repository.sendAnswer(classroomArgs!!, answer)


            when (result) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE

                    Log.d("Answer", "answer=${result.data}")

                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR

                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR

                }
                else -> {
                    _error.value = MandarinLearningApplication.instance.toString()
                    _status.value = LoadApiStatus.ERROR

                }
            }
            _refreshStatus.value = false
        }

    }

//    // Get answer output
//    private fun getAnswerOutput() {
//
//        coroutineScope.launch {
//
//            _status.value = LoadApiStatus.LOADING
//
//            val result = repository.getAnswerOutput(classroomArgs!!, answer)
//
//            when (result) {
//                is Result.Success -> {
//                    _error.value = null
//                    _status.value = LoadApiStatus.DONE
//                    questionList.addAll(result.data)
//                    Log.d("Answer", "Answers=${result.data}")
//
//                }
//                is Result.Fail -> {
//                    _error.value = result.error
//                    _status.value = LoadApiStatus.ERROR
//
//                }
//                is Result.Error -> {
//                    _error.value = result.exception.toString()
//                    _status.value = LoadApiStatus.ERROR
//
//                }
//                else -> {
//                    _error.value = MandarinLearningApplication.instance.toString()
//                    _status.value = LoadApiStatus.ERROR
//
//                }
//            }
//            _refreshStatus.value = false
//        }
//    }




    fun next() {

        var size = questionList.size

        if (position < size) {

            position += 1

            if (position < size) {
                _questionData.value = questionList[position]  //有下一題
            }

        } else {
            _showToast.value = 1 //沒有下一題了
        }
    }

    fun resetShowToast() {
        Log.d("resetShowToast", "resetShowToast")
        _showToast.value = null
    }

    fun back() {
        if (position > 0) {
            position -= 1

            if (position >= 0) {
                _questionData.value = questionList[position]
            }

        } else {
            _showToast.value = 0 //第一題
        }

    }


//    private fun getCourseData(id: String) {
//
//        if (id == "C01") {
//            //Question for course1
//            val question101 = Question(1, "回家/昨天/晚上/你/幾點?", "Sentence Reodering", "你昨天晚上幾點回家?")
//            val question102 = Question(2, "沒有/昨天/回家/通宵/我", "Sentence Reodering", "我昨天通宵沒有回家")
//            val questionList1 = mutableListOf<Question>()
//            questionList1.add(question101)
//            questionList1.add(question102)
//            //Course1
//            val course1 = Course(
//                "C01",
//                "Mandarin 101",
//                "Intermediate",
//                "3",
//                "O001",
//                1,
//                20201203,
//                "null"
//            )
//
//            courseData = course1
//            questionList = questionList1
//
//        } else if (id == "C02") {
//            //Question for course2
//            val question201 = Question(1, "過得/今天/如何?", "Sentence Reodering", "今天過得如何?")
//            val question202 = Question(2, "糟/今天/一樣/過得", "Sentence Reodering", "今天過得一樣糟")
//            val questionList2 = mutableListOf<Question>()
//            questionList2.add(question201)
//            questionList2.add(question202)
//            //Course2
//            val course2 = Course(
//                "C02",
//                "Learning Mandarin So Much Fun",
//                "Basic",
//                "5",
//                "O002",
//                0,
//                20201201,
//                "null"
//            )
//
//
//            courseData = course2
//            questionList = questionList2
//
//        }
//
//    }


}



