package com.taiyilin.mandarinlearning.data.source

import com.taiyilin.mandarinlearning.data.Result
import com.taiyilin.mandarinlearning.data.Course


// Main entry point for accessing Publisher sources.
interface MandarinLearningDataSource {

    suspend fun getAllCourses(): Result<List<Course>>

//    suspend fun login(id: String): Result<Author>
//
//    suspend fun getArticles(): Result<List<Article>>
//
//    fun getLiveArticles(): MutableLiveData<List<Article>>
//
//    suspend fun publish(article: Article): Result<Boolean>
//
//    suspend fun delete(article: Article): Result<Boolean>

}