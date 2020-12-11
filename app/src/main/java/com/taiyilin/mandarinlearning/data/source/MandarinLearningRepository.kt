package com.taiyilin.mandarinlearning.data.source

import androidx.lifecycle.MutableLiveData
import com.taiyilin.mandarinlearning.data.Classroom
import com.taiyilin.mandarinlearning.data.Result
import com.taiyilin.mandarinlearning.data.Course

//Interface to the Publisher layers
interface MandarinLearningRepository {

    suspend fun getAllCourses(): Result<List<Course>>

    suspend fun addSelectedCourse(classroom: Classroom): Result<Boolean>

    fun getLiveCourses(): MutableLiveData<List<Course>>

    suspend fun updateCourse(courseId: String, studentId: String): Result<Boolean>

}