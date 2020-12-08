package com.taiyilin.mandarinlearning.ext

import androidx.fragment.app.Fragment
import com.taiyilin.mandarinlearning.MandarinLearningApplication
import com.taiyilin.mandarinlearning.factory.ViewModelFactory

//Extension functions for Fragment.
fun Fragment.getVmFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as MandarinLearningApplication).repository
    return ViewModelFactory(repository)
}


//fun Fragment.getVmFactory(author: Author?): AuthorViewModelFactory {
//    val repository = (requireContext().applicationContext as PublisherApplication).repository
//    return AuthorViewModelFactory(repository, author)
//}