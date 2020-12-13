package com.taiyilin.mandarinlearning.data.source

import androidx.lifecycle.MutableLiveData
import com.taiyilin.mandarinlearning.data.Classroom
import com.taiyilin.mandarinlearning.data.Result
import com.taiyilin.mandarinlearning.data.Course


// Main entry point for accessing Mandarin Learning sources.
interface MandarinLearningDataSource {

    suspend fun getAllCourses(): Result<List<Course>>

    suspend fun addSelectedCourse(classroom: Classroom) :Result<Boolean>

    suspend fun updateCourse(courseId: String, studentId: String): Result<Boolean>

    fun getAllLiveCourses(): MutableLiveData<List<Course>>

    fun getUserLiveCourse(): MutableLiveData<List<Course>>

}