package com.taiyilin.mandarinlearning.homeDetail

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
import java.util.Collections.addAll

class HomeDetailViewModel(
    private val repository: MandarinLearningRepository,
    private val courseArgs: Course?
) : ViewModel() {

    //Course data argument from Classroom Fragment
    private val _courseDetail = MutableLiveData<Course>().apply {
        value = courseArgs
    }
    val courseDetail: LiveData<Course>
    get() = _courseDetail


    //Feedback data in Course
    private val _feedbackList = MutableLiveData<List<Feedback>>()
    val feedbackList: LiveData<List<Feedback>>
    get() = _feedbackList


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

        _courseDetail.value = courseArgs
        getCourseFeedback()

    }

    private fun getCourseFeedback() {

            coroutineScope.launch {

                _status.value = LoadApiStatus.LOADING

                val result = repository.getFeedback(courseArgs!!)

                when (result) {
                    is Result.Success -> {
                        _error.value = null
                        _status.value = LoadApiStatus.DONE

                        _feedbackList.value = result.data
                        Log.d("Question", "Questions=${result.data}")

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



    }





