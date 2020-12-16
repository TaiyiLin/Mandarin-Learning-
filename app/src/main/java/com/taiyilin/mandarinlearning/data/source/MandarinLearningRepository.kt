package com.taiyilin.mandarinlearning.data.source

import androidx.lifecycle.MutableLiveData
import com.taiyilin.mandarinlearning.data.*

//Interface to the Publisher layers
interface MandarinLearningRepository {

    suspend fun getAllCourses(): Result<List<Course>>

    suspend fun addSelectedCourse(classroom: Classroom): Result<Boolean>

    suspend fun updateCourse(courseId: String, studentId: String): Result<Boolean>

    fun getAllLiveCourses(): MutableLiveData<List<Course>>

    fun getUserLiveCourse(): MutableLiveData<List<Course>>

    suspend fun getAllClassrooms(): Result<List<Classroom>>

    fun getLiveClassrooms(): MutableLiveData<List<Classroom>>

    suspend fun getQuestions(classroom: Classroom): Result<List<Question>>

    fun getAllLiveMessages(classroom: Classroom): MutableLiveData<List<Message>>

    suspend fun sendAnswer(classroom: Classroom, answer: Answer): Result<Answer>

}