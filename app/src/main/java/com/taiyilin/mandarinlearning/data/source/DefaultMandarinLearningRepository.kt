package com.taiyilin.mandarinlearning.data.source

import androidx.lifecycle.MutableLiveData
import com.taiyilin.mandarinlearning.data.Classroom
import com.taiyilin.mandarinlearning.data.Result
import com.taiyilin.mandarinlearning.data.Course


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
}