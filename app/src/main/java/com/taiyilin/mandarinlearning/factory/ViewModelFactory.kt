package com.taiyilin.mandarinlearning.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.taiyilin.mandarinlearning.MainViewModel
import com.taiyilin.mandarinlearning.data.source.MandarinLearningRepository
import com.taiyilin.mandarinlearning.main.classroom.ClassroomViewModel
import com.taiyilin.mandarinlearning.main.home.HomeViewModel
import com.taiyilin.mandarinlearning.main.profile.ProfileViewModel
import com.taiyilin.mandarinlearning.pickRole.PickRoleViewModel
import com.taiyilin.mandarinlearning.sentenceReordering.SentenceReorderingViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val repository: MandarinLearningRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) ->
                    MainViewModel(repository)

                isAssignableFrom(PickRoleViewModel::class.java) ->
                    PickRoleViewModel(repository)

                isAssignableFrom(HomeViewModel::class.java) ->
                    HomeViewModel(repository)

                isAssignableFrom(ClassroomViewModel::class.java) ->
                    ClassroomViewModel(repository)

                isAssignableFrom(ProfileViewModel::class.java) ->
                     ProfileViewModel(repository)


                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
