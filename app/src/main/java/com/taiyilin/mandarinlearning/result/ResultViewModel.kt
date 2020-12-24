package com.taiyilin.mandarinlearning.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.taiyilin.mandarinlearning.data.Classroom
import com.taiyilin.mandarinlearning.data.Question
import com.taiyilin.mandarinlearning.data.source.MandarinLearningRepository
import com.taiyilin.mandarinlearning.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class ResultViewModel(private val repository: MandarinLearningRepository, classroom: Classroom) :
    ViewModel() {

    //Get Classroom list
    private var _questionList = MutableLiveData<List<Question>>()
    val questionList: LiveData<List<Question>>
        get() = _questionList


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

        getBothAnswer(classroom)

    }


    //重組list
    fun getBothAnswer(classroom: Classroom) {

        //重組list
        val list = mutableListOf<Question>()

        for (question in classroom.questionList!!) {
            for (answer in classroom.answerList!!) {
                if (question.number == answer.questionNumber) {
                    question.studentAnswer = answer.answer
                    list.add(question)
                }
            }
        }

        _questionList.value = list
    }


}