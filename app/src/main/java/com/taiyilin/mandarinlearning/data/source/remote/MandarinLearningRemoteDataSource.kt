package com.taiyilin.mandarinlearning.data.source.remote

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.taiyilin.mandarinlearning.data.Result
import com.taiyilin.mandarinlearning.data.Course
import com.taiyilin.mandarinlearning.data.Feedback
import com.taiyilin.mandarinlearning.data.source.MandarinLearningDataSource
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

//Implementation of the Mandarin Learning source that from network.
object MandarinLearningRemoteDataSource :
    MandarinLearningDataSource {

    private val db = FirebaseFirestore.getInstance()

    override suspend fun getAllCourses(): Result<List<Course>> = suspendCoroutine { continuation ->

        db.collection("Course")
//            .orderBy(KEY_CREATED_TIME, Query.Direction.DESCENDING)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<Course>()
                    var count = 0
                    for (document in task.result!!) {
                        Log.d("Taiyi", document.id + " => " + document.data)

                        db.collection("Course").document(document.id).collection("Feedback").get()
                            .addOnCompleteListener { task1 ->
                                if (task1.isSuccessful) {
                                    val feedbackList = mutableListOf<Feedback>()
                                    for (fbDocument in task1.result!!){
                                        val feedback = fbDocument.toObject(Feedback::class.java)
                                        feedbackList.add(feedback)

                                    }
                                    //把document轉成我們需要資料的格式Course
                                    val course = document.toObject(Course::class.java)
                                    course.feedbackList = feedbackList
                                    list.add(course)
                                    count += 1
                                    if (count == task.result!!.size()) {
                                        continuation.resume(Result.Success(list))
                                    }

                                } else {
                                    task1.exception?.let {
                                        Log.w(
                                            "ty", "000"
                                        )
                                        continuation.resume(Result.Error(it))
                                        return@addOnCompleteListener
                                    }
                                }

                            }


                    }

                } else {
                    task.exception?.let {

                        Log.w(
                            "Taiyi",
                            "[${this::class.simpleName}] Error getting documents. ${it.message}"
                        )
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
//                    continuation.resume(Result.Fail(PublisherApplication.instance.getString(R.string.you_know_nothing)))
                    continuation.resume(Result.Fail("Taiyiyiyiyiyiyiyi"))
                }
            }
    }

//    override fun getLiveArticles(): MutableLiveData<List<Article>> {
//
//        val liveData = MutableLiveData<List<Article>>()
//
//        FirebaseFirestore.getInstance()
//            .collection(PATH_ARTICLES)
//            .orderBy(KEY_CREATED_TIME, Query.Direction.DESCENDING)
//            .addSnapshotListener { snapshot, exception ->
//
//                Logger.i("addSnapshotListener detect")
//
//                exception?.let {
//                    Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
//                }
//
//                val list = mutableListOf<Article>()
//                for (document in snapshot!!) {
//                    Logger.d(document.id + " => " + document.data)
//
//                    val article = document.toObject(Article::class.java)
//                    list.add(article)
//                }
//
//                liveData.value = list
//            }
//        return liveData
//    }
//
//    override suspend fun publish(article: Article): Result<Boolean> = suspendCoroutine { continuation ->
//        val articles = FirebaseFirestore.getInstance().collection(PATH_ARTICLES)
//        val document = articles.document()
//
//        article.id = document.id
//        article.createdTime = Calendar.getInstance().timeInMillis
//
//        document
//            .set(article)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Logger.i("Publish: $article")
//
//                    continuation.resume(Result.Success(true))
//                } else {
//                    task.exception?.let {
//
//                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
//                        continuation.resume(Result.Error(it))
//                        return@addOnCompleteListener
//                    }
//                    continuation.resume(Result.Fail(PublisherApplication.instance.getString(R.string.you_know_nothing)))
//                }
//            }
//    }
//
//    override suspend fun delete(article: Article): Result<Boolean> = suspendCoroutine { continuation ->
//
//        when {
//            article.author?.id == "waynechen323"
//                    && article.tag.toLowerCase(Locale.TAIWAN) != "test"
//                    && article.tag.trim().isNotEmpty() -> {
//
//                continuation.resume(Result.Fail("You know nothing!! ${article.author?.name}"))
//            }
//            else -> {
//                FirebaseFirestore.getInstance()
//                    .collection(PATH_ARTICLES)
//                    .document(article.id)
//                    .delete()
//                    .addOnSuccessListener {
//                        Logger.i("Delete: $article")
//
//                        continuation.resume(Result.Success(true))
//                    }.addOnFailureListener {
//                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
//                        continuation.resume(Result.Error(it))
//                    }
//            }
//        }
//
//    }
//


}