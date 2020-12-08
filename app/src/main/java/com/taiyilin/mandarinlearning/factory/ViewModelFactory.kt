package com.taiyilin.mandarinlearning.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.taiyilin.mandarinlearning.data.MandarinLearningRepository

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val repository: MandarinLearningRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
//                isAssignableFrom(MainViewModel::class.java) ->
//                    MainViewModel(repository)
//
//                isAssignableFrom(HomeViewModel::class.java) ->
//                    HomeViewModel(repository)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
