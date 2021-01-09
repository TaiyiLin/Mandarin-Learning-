package com.taiyilin.mandarinlearning.teacherClassroom


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.taiyilin.mandarinlearning.data.Classroom
import com.taiyilin.mandarinlearning.data.source.MandarinLearningRepository
import com.taiyilin.mandarinlearning.login.UserManager
import com.taiyilin.mandarinlearning.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class TeacherClassroomViewModel(private val repository: MandarinLearningRepository) : ViewModel() {

    //Get live classroom data
    var liveClassrooms = MutableLiveData<List<Classroom>>()

    //pass args to detail page
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

        getTLiveClassroom()

    }

    private fun getTLiveClassroom() {
        UserManager.userUID?.let {
            liveClassrooms = repository.getTLiveClassrooms(it)
            _status.value = LoadApiStatus.DONE
            _refreshStatus.value = false
        }

    }

    fun navigateToDetail (classroom: Classroom){
        Log.wtf("123123","123wtf")
        _navigateToDetail.value = classroom
    }

    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }

}