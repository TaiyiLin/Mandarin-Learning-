package com.taiyilin.mandarinlearning.data.source.local

import android.content.Context
import com.taiyilin.mandarinlearning.data.Result
import com.taiyilin.mandarinlearning.data.Course
import com.taiyilin.mandarinlearning.data.source.MandarinLearningDataSource


//Concrete implementation of a Publisher source as a db.
class MandarinLearningLocalDataSource(val context: Context) :
    MandarinLearningDataSource {


    override suspend fun getAllCourses(): Result<List<Course>> {
        TODO("Not yet implemented")
    }

//    override suspend fun login(id: String): Result<Author> {
//        return when (id) {
//            "waynechen323" -> Result.Success((Author(
//                id,
//                "AKA小安老師",
//                "wayne@school.appworks.tw"
//            )))
//            "dlwlrma" -> Result.Success((Author(
//                id,
//                "IU",
//                "dlwlrma@school.appworks.tw"
//            )))
//            //TODO add your profile here
//            else -> Result.Fail("You have to add $id info in local data source")
//        }
//    }
//
//    override suspend fun getArticles(): Result<List<Article>> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getLiveArticles(): MutableLiveData<List<Article>> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override suspend fun publish(article: Article): Result<Boolean> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override suspend fun delete(article: Article): Result<Boolean> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//


}