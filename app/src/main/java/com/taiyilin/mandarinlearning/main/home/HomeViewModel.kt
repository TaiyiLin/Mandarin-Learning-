package com.taiyilin.mandarinlearning.main.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.taiyilin.mandarinlearning.MandarinLearningApplication
import com.taiyilin.mandarinlearning.R
import com.taiyilin.mandarinlearning.data.Classroom
import com.taiyilin.mandarinlearning.data.Course
import com.taiyilin.mandarinlearning.data.source.MandarinLearningRepository
import com.taiyilin.mandarinlearning.data.source.remote.MandarinLearningRemoteDataSource.getAllCourses
import com.taiyilin.mandarinlearning.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.taiyilin.mandarinlearning.data.Result
import com.taiyilin.mandarinlearning.login.UserManager

class HomeViewModel(private val repository: MandarinLearningRepository) : ViewModel() {

    private var _course = MutableLiveData<List<Course>>()

    val course: LiveData<List<Course>>
    get() = _course


    var liveCourses = MutableLiveData<List<Course>>()


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

        getAllCoursesResult()

    }

    fun getAllCoursesResult() {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getAllCourses()

            _course.value = when (result) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    result.data
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                else -> {
//                    _error.value = MandarinLearningApplication.instance
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
            _refreshStatus.value = false
        }
    }


    fun addSelectedCourse(course: Course) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val classroom = Classroom()
            classroom.courseId = course.id
            classroom.studentId = UserManager.userUID!!
            classroom.teacherId = course.ownerId

            when (val result = repository.addSelectedCourse(classroom)) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
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
//                    _error.value = MandarinLearningApplication.instance.getString(R.string.you_know_nothing)
                    _status.value = LoadApiStatus.ERROR
                }
            }
        }
    }

    fun getLiveCoursesResult(){
        liveCourses = repository.getLiveCourses()
        _status.value = LoadApiStatus.DONE
        _refreshStatus.value = false
    }

    fun updateCourse(courseId: String, studentId: String){
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.updateCourse(courseId,studentId)

            when (result) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE

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
//                    _error.value = MandarinLearningApplication.instance
                    _status.value = LoadApiStatus.ERROR

                }
            }
            _refreshStatus.value = false
        }

    }
}