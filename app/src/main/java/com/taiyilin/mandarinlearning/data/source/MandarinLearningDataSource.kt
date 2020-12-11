package com.taiyilin.mandarinlearning.data.source

import androidx.lifecycle.MutableLiveData
import com.taiyilin.mandarinlearning.data.Classroom
import com.taiyilin.mandarinlearning.data.Result
import com.taiyilin.mandarinlearning.data.Course


// Main entry point for accessing Publisher sources.
interface MandarinLearningDataSource {

    suspend fun getAllCourses(): Result<List<Course>>

    suspend fun addSelectedCourse(classroom: Classroom) :Result<Boolean>

    fun getLiveCourses(): MutableLiveData<List<Course>>

    suspend fun updateCourse(courseId: String, studentId: String): Result<Boolean>


}