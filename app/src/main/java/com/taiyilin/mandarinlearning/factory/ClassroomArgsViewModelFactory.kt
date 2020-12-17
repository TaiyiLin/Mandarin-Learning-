package com.taiyilin.mandarinlearning.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.taiyilin.mandarinlearning.data.Classroom
import com.taiyilin.mandarinlearning.data.source.MandarinLearningRepository
import com.taiyilin.mandarinlearning.sentenceReordering.SentenceReorderingViewModel
import java.lang.IllegalArgumentException


//Factory for all ViewModels which needs [Classroom]
class ClassroomArgsViewModelFactory (
    private val repository: MandarinLearningRepository,
    private val classroomArgs: Classroom?
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SentenceReorderingViewModel::class.java)){
            return SentenceReorderingViewModel(repository, classroomArgs) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

}