package com.taiyilin.mandarinlearning.main.classroom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.taiyilin.mandarinlearning.MandarinLearningApplication
import com.taiyilin.mandarinlearning.data.Classroom
import com.taiyilin.mandarinlearning.data.Course
import com.taiyilin.mandarinlearning.data.source.MandarinLearningRepository
import com.taiyilin.mandarinlearning.data.Question
import com.taiyilin.mandarinlearning.data.Result
import com.taiyilin.mandarinlearning.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ClassroomViewModel(private val repository: MandarinLearningRepository) : ViewModel() {


    val course = MutableLiveData<Course>()


    //Get Classroom list
    private var _classroom = MutableLiveData<List<Classroom>>()
    val classroom: LiveData<List<Classroom>>
        get() = _classroom

    var liveClassrooms = MutableLiveData<List<Classroom>>()


    private val _navigateToDetail = MutableLiveData<Classroom>()
     val navigateToDetail: LiveData<Classroom>
    get() = _navigateToDetail


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
        getAllClassroomResult()
        getLiveClassroomsResult()
    }

    private fun getAllClassroomResult() {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getAllClassrooms()

            _classroom.value = when (result) {
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
                    _error.value = MandarinLearningApplication.instance.toString()
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
            _refreshStatus.value = false
        }
    }

    private fun getLiveClassroomsResult() {
        liveClassrooms = repository.getLiveClassrooms()
        _status.value = LoadApiStatus.DONE
        _refreshStatus.value = false
    }





//
//
//    fun getCourseById(id: String) {
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
//            course.value = course1
//        } else if(id == "C02"){
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
//            course.value = course2
//        }
//
//
//
//        }
//

    fun navigateToDetail(classroom: Classroom) {
        _navigateToDetail.value = classroom
    }

    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }

    }

