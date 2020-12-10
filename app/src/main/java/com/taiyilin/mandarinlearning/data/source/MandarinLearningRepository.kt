package com.taiyilin.mandarinlearning.data.source

import com.taiyilin.mandarinlearning.data.Result
import com.taiyilin.mandarinlearning.data.Course

//Interface to the Publisher layers
interface MandarinLearningRepository {

    suspend fun getAllCourses(): Result<List<Course>>

//    suspend fun loginMockData(id: String): Result<Author>
//
//    suspend fun getArticles(): Result<List<Article>>
//
//    fun getLiveArticles(): MutableLiveData<List<Article>>
//
//    suspend fun publish(article: Article): Result<Boolean>
//
//    suspend fun delete(article: Article): Result<Boolean>

}