package com.taiyilin.mandarinlearning

import androidx.lifecycle.ViewModel
import com.taiyilin.mandarinlearning.data.source.MandarinLearningRepository


class MainViewModel( private val repository: MandarinLearningRepository): ViewModel() {

//    fun loginAndSetUser(userUID: String, userName: String) {
//        coroutineScope.launch {
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
//                    _error.value = GogoyoApplication.instance.getString(R.string.something_wrong)
//                    _status.value = LoadStatus.ERROR
//                }
//            }
//        }
//    }

}