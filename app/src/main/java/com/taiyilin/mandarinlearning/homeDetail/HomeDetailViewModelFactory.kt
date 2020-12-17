package com.taiyilin.mandarinlearning.homeDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.taiyilin.mandarinlearning.data.Classroom
import com.taiyilin.mandarinlearning.data.Course
import com.taiyilin.mandarinlearning.data.source.MandarinLearningRepository
import com.taiyilin.mandarinlearning.sentenceReordering.SentenceReorderingFragmentArgs
import com.taiyilin.mandarinlearning.sentenceReordering.SentenceReorderingViewModel

class HomeDetailViewModelFactory(
    private val courseArgs: Course,
    private val repository: MandarinLearningRepository
) : ViewModelProvider.Factory   {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeDetailViewModel::class.java)) {
            return HomeDetailViewModel(repository, courseArgs) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}



