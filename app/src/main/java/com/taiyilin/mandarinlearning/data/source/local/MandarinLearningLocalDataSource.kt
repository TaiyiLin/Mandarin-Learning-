package com.taiyilin.mandarinlearning.data.source.local

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.taiyilin.mandarinlearning.data.Classroom
import com.taiyilin.mandarinlearning.data.Result
import com.taiyilin.mandarinlearning.data.Course
import com.taiyilin.mandarinlearning.data.source.MandarinLearningDataSource


//Concrete implementation of a Publisher source as a db.
class MandarinLearningLocalDataSource(val context: Context) :
    MandarinLearningDataSource {


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


}