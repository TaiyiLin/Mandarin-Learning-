package com.taiyilin.mandarinlearning.sentenceReordering

import android.app.Application
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.taiyilin.mandarinlearning.data.Classroom

class SentenceReorderingViewModel(classroomData: Classroom, application: Application) : ViewModel() {

    private val _classroomData = MutableLiveData<Classroom>().apply {
        value = classroomData
    }

    val classroomData: LiveData<Classroom>
    get()=_classroomData

    init {
        _classroomData.value = classroomData
    }

}
