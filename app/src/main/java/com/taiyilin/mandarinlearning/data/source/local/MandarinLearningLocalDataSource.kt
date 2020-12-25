package com.taiyilin.mandarinlearning.data.source.local

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.taiyilin.mandarinlearning.data.*
import com.taiyilin.mandarinlearning.data.source.MandarinLearningDataSource


//Concrete implementation of a Publisher source as a db.
class MandarinLearningLocalDataSource(val context: Context) :
    MandarinLearningDataSource {

    override suspend fun getUser(id: String, name: String): Result<User> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(user: User): Result<Boolean>{
        TODO("Not yet implemented")
    }

    override suspend fun getAllCourses(): Result<List<Course>> {
        TODO("Not yet implemented")
    }

    override suspend fun addSelectedCourse(classroom: Classroom): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun updateCourse(courseId: String, studentId: String): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override fun getAllLiveCourses(): MutableLiveData<List<Course>> {
        TODO("Not yet implemented")
    }

    override fun getUserLiveCourse(): MutableLiveData<List<Course>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllClassrooms(): Result<List<Classroom>> {
        TODO("Not yet implemented")
    }

    override fun getLiveClassrooms(): MutableLiveData<List<Classroom>> {
        TODO("Not yet implemented")
    }

    override suspend fun getQuestions(classroom: Classroom): Result<List<Question>> {
        TODO("Not yet implemented")
    }

    override fun getAllLiveMessages(classroom: Classroom): MutableLiveData<List<Message>> {
        TODO("Not yet implemented")
    }

    override suspend fun sendAnswer(classroom: Classroom, answer: Answer): Result<Answer> {
        TODO("Not yet implemented")
    }

    override fun getLiveAnswer(classroom: Classroom): MutableLiveData<List<Answer>> {
        TODO("Not yet implemented")
    }


    override suspend fun getFeedback(course: Course): Result<List<Feedback>> {
        TODO("Not yet implemented")
    }

    override suspend fun sendMessage(classroom: Classroom, message: Message): Result<Boolean> {
        TODO("Not yet implemented")
    }


}