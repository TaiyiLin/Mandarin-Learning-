package com.taiyilin.mandarinlearning.ext

import androidx.fragment.app.Fragment
import com.taiyilin.mandarinlearning.MandarinLearningApplication
import com.taiyilin.mandarinlearning.data.Classroom
import com.taiyilin.mandarinlearning.data.Course
import com.taiyilin.mandarinlearning.factory.ClassroomArgsViewModelFactory
import com.taiyilin.mandarinlearning.factory.ViewModelFactory
import com.taiyilin.mandarinlearning.factory.HomeDetailViewModelFactory

//Extension functions for Fragment.
fun Fragment.getVmFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as MandarinLearningApplication).repository
    return ViewModelFactory(repository)
}


fun Fragment.getVmFactory(classroomArgs: Classroom?): ClassroomArgsViewModelFactory {
    val repository = (requireContext().applicationContext as MandarinLearningApplication).repository
    return ClassroomArgsViewModelFactory(repository, classroomArgs)
}


fun Fragment.getVmFactory(courseArgs: Course): HomeDetailViewModelFactory {
    val repository = (requireContext().applicationContext as MandarinLearningApplication).repository
    return HomeDetailViewModelFactory(
        courseArgs,
        repository
    )
}

