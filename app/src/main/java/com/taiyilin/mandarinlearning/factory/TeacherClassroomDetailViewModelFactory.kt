package com.taiyilin.mandarinlearning.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.taiyilin.mandarinlearning.data.Classroom
import com.taiyilin.mandarinlearning.data.source.MandarinLearningRepository
import com.taiyilin.mandarinlearning.sentenceReordering.SentenceReorderingViewModel
import com.taiyilin.mandarinlearning.teacherClassroomDetail.TeacherClassroomDetailViewModel


class TeacherClassroomDetailViewModelFactory(
    private val classroomArgs: Classroom,
    private val repository: MandarinLearningRepository
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TeacherClassroomDetailViewModel::class.java)) {
            return TeacherClassroomDetailViewModel(
                repository,
                classroomArgs
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
