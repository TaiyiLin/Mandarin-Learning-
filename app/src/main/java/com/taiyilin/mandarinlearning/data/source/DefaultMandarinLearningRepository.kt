package com.taiyilin.mandarinlearning.data.source

import androidx.lifecycle.MutableLiveData
import com.taiyilin.mandarinlearning.data.*


// Concrete implementation to load Mandarin Learning sources.
class DefaultMandarinLearningRepository(
    private val remoteDataSource: MandarinLearningDataSource,
    private val localDataSource: MandarinLearningDataSource
) : MandarinLearningRepository {

    override suspend fun getAllCourses(): Result<List<Course>> {
        return remoteDataSource.getAllCourses()
    }

    override suspend fun addSelectedCourse(classroom: Classroom): Result<Boolean> {
        return remoteDataSource.addSelectedCourse(classroom)
    }

    override suspend fun updateCourse(courseId: String, studentId: String): Result<Boolean> {
        return remoteDataSource.updateCourse(courseId, studentId)
    }

    override fun getAllLiveCourses(): MutableLiveData<List<Course>> {
        return remoteDataSource.getAllLiveCourses()
    }

    override fun getUserLiveCourse(): MutableLiveData<List<Course>> {
        return remoteDataSource.getUserLiveCourse()
    }

    override suspend fun getAllClassrooms(): Result<List<Classroom>> {
        return remoteDataSource.getAllClassrooms()
    }

    override fun getLiveClassrooms(): MutableLiveData<List<Classroom>> {
        return remoteDataSource.getLiveClassrooms()
    }

    override suspend fun getQuestions(classroom: Classroom): Result<List<Question>> {
        return remoteDataSource.getQuestions(classroom)
    }

    override fun getAllLiveMessages(classroom: Classroom): MutableLiveData<List<Message>> {
        return remoteDataSource.getAllLiveMessages(classroom)
    }

    override suspend fun sendAnswer(classroom: Classroom, answer: Answer): Result<Answer> {
        return remoteDataSource.sendAnswer(classroom, answer)
     }

    override suspend fun getAnswerOutput(classroom: Classroom, answer: Answer): Result<Answer> {
        TODO("Not yet implemented")
    }

//    override suspend fun getAnswerOutput(classroom: Classroom, answer: Answer): Result<Answer>{
//        return remoteDataSource.getAnswerOutput(classroom, answer)
//    }

}