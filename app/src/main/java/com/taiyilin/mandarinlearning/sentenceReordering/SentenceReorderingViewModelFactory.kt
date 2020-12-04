package com.taiyilin.mandarinlearning.sentenceReordering

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.taiyilin.mandarinlearning.data.Classroom


class SentenceReorderingViewModelFactory(
    private val classroomData: Classroom,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SentenceReorderingViewModel::class.java)) {
            return SentenceReorderingViewModel(classroomData, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}