package com.taiyilin.mandarinlearning.sentenceReordering

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.taiyilin.mandarinlearning.data.Classroom
import com.taiyilin.mandarinlearning.data.source.MandarinLearningRepository
import com.taiyilin.mandarinlearning.util.ServiceLocator.repository


class SentenceReorderingViewModelFactory(
    private val classroomArgs: Classroom,
    private val repository: MandarinLearningRepository
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SentenceReorderingViewModel::class.java)) {
            return SentenceReorderingViewModel(repository, classroomArgs) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
