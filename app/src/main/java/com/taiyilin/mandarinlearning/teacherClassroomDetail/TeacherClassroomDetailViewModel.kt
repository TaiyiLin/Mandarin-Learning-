package com.taiyilin.mandarinlearning.teacherClassroomDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.taiyilin.mandarinlearning.MandarinLearningApplication
import com.taiyilin.mandarinlearning.data.Classroom
import com.taiyilin.mandarinlearning.data.Message
import com.taiyilin.mandarinlearning.data.Question
import com.taiyilin.mandarinlearning.data.Result
import com.taiyilin.mandarinlearning.data.source.MandarinLearningRepository
import com.taiyilin.mandarinlearning.login.UserManager
import com.taiyilin.mandarinlearning.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TeacherClassroomDetailViewModel(
    private val repository: MandarinLearningRepository,
    private val classroomArgs: Classroom?
) : ViewModel() {

    //Classroom Data argument from ClassroomFragment
    private val _classroomData = MutableLiveData<Classroom>().apply {
        value = classroomArgs
    }
    val classroomData: LiveData<Classroom>
        get() = _classroomData


    //Classroom data with questionList passing to ResultFragment
    private val _classroomWithAnswers = MutableLiveData<Classroom>()
    val classroomWithAnswers: LiveData<Classroom>
        get() = _classroomWithAnswers


    //Question Data
    var questionList = mutableListOf<Question>()

    private val _questionData = MutableLiveData<Question>()
    val questionData: LiveData<Question>
        get() = _questionData

    var position = 0
    var position1 = MutableLiveData<Int>()

    //For blocking clicking next question
    private val _showToast = MutableLiveData<Int>()
    val showToast: LiveData<Int>
        get() = _showToast

    var liveMessage = MutableLiveData<List<Message>>()

    //Set Message
    val messageContent = MutableLiveData<String>()



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
        getQuestionsResult()
        getAllLiveMessages()

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

    // 輸入聊天室訊息
    fun sendMessage() {
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING


            // set message info
            val message = Message()
            message.apply {
                content = messageContent.value ?: "" //?:如果前面值為null 就給後面的""
                senderId = UserManager.userUID!!
                receiverId = when (UserManager.userType) {
                    "student" -> {
                        classroomArgs!!.teacherId
                    }
                    else -> {
                        classroomArgs!!.studentId
                    }
                }
            }
            val result = repository.sendMessage(classroomArgs!!, message)

            when (result) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE

                    Log.d("Message", "message=${result.data}")

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

    fun next() {

        var size = questionList.size

        if (position < size) {

            position += 1
            position1.value = position

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

            position1.value = position

            if (position >= 0) {
                _questionData.value = questionList[position]
            }

        } else {
            _showToast.value = 0 //第一題
        }

    }


}