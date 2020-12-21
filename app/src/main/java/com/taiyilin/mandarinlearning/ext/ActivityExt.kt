package com.taiyilin.mandarinlearning.ext

import android.app.Activity
import android.view.Gravity
import android.widget.Toast
import com.taiyilin.mandarinlearning.MandarinLearningApplication
import com.taiyilin.mandarinlearning.factory.ViewModelFactory

//Extension functions for Activity.
fun Activity.getVmFactory(): ViewModelFactory {
    val repository = (applicationContext as MandarinLearningApplication).repository
    return ViewModelFactory(repository)
}

fun Activity?.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).apply {
        setGravity(Gravity.TOP, 0, 0)
        show()
    }
}
