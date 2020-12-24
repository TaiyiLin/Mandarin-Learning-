package com.taiyilin.mandarinlearning.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.taiyilin.mandarinlearning.MandarinLearningApplication
import com.taiyilin.mandarinlearning.data.*
import com.taiyilin.mandarinlearning.data.source.MandarinLearningRepository
import com.taiyilin.mandarinlearning.login.UserManager
import com.taiyilin.mandarinlearning.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LogInViewModel(private val repository: MandarinLearningRepository): ViewModel() {


    //get Firebase user
    private var _userData = MutableLiveData<User>()
    val userData: LiveData<User>
        get() = _userData


    private var _intentToPickType = MutableLiveData<Boolean>()
    val intentToPickType: LiveData<Boolean>
        get() = _intentToPickType


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



    }


    //Get User data
    fun getUser(id: String, name: String) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getUser(id, name)

            when (result) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE

                    val user = result.data
                    if (user.type == ""){
                        _intentToPickType.value = true
                    }
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
        }
    }




//    fun loginAndSetUser(userUID: String, userName: String) {
//        coroutineScope.launch {
//
//            _status.value = LoadStatus.LOADING
//            when (val result = repository.login(userUID, userName)) {
//                is Result.Success -> {
//                    _error.value = null
//                    _status.value = LoadStatus.DONE
//                }
//                is Result.Fail -> {
//                    _error.value = result.error
//                    _status.value = LoadStatus.ERROR
//                }
//                is Result.Error -> {
//                    _error.value = result.exception.toString()
//                    _status.value = LoadStatus.ERROR
//                }
//                else -> {
//                    _error.value = MandarinLearningApplication.instance.getString(R.string.something_wrong)
//                    _status.value = LoadStatus.ERROR
//                }
//            }
//        }
//    }

}