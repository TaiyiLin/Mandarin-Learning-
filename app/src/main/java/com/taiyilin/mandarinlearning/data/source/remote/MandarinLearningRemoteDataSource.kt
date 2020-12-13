package com.taiyilin.mandarinlearning.data.source.remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.taiyilin.mandarinlearning.MandarinLearningApplication
import com.taiyilin.mandarinlearning.R
import com.taiyilin.mandarinlearning.data.Classroom
import com.taiyilin.mandarinlearning.data.Result
import com.taiyilin.mandarinlearning.data.Course
import com.taiyilin.mandarinlearning.data.Feedback
import com.taiyilin.mandarinlearning.data.source.MandarinLearningDataSource
import com.taiyilin.mandarinlearning.login.UserManager
import com.taiyilin.mandarinlearning.util.Logger
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
                                    for (fbDocument in task1.result!!) {
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


    override suspend fun addSelectedCourse(classroom: Classroom): Result<Boolean> = suspendCoroutine { continuation ->

        val classroomRef = FirebaseFirestore.getInstance().collection("Classroom")
        //val document的document 是一個空資料夾
        val document = classroomRef.document()

        classroom.id = document.id

        document
            .set(classroom)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
//                    Logger.i("Publish: $classroom")

                    continuation.resume(Result.Success(true))
                } else {
                    task.exception?.let {

//                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(""))
                }
            }

    }


    override suspend fun updateCourse(
        courseId: String, studentId: String): Result<Boolean> = suspendCoroutine { continuation ->

        val courseRef = FirebaseFirestore.getInstance().collection("Course")

        courseRef.document(courseId)
            .update("studentList", FieldValue.arrayUnion(studentId))
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
//                    Logger.i("Course: $courseId")

                    continuation.resume(Result.Success(true))
                } else {
                    task.exception?.let {

//                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(""))
                }
            }
    }

    //監聽資料、持續更新
    override fun getAllLiveCourses(): MutableLiveData<List<Course>> {

        val liveData = MutableLiveData<List<Course>>()

        FirebaseFirestore.getInstance()
            .collection("Course")
//          .orderBy(KEY_CREATED_TIME, Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, exception ->

//                Logger.i("addSnapshotListener detect")

                exception?.let {
//                    Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                }

                val list = mutableListOf<Course>()
                for (document in snapshot!!) {
//                    Logger.d(document.id + " => " + document.data)

                    val course = document.toObject(Course::class.java)
                    list.add(course)
                }

                liveData.value = list
            }
        return liveData

    }


    override fun getUserLiveCourse(): MutableLiveData<List<Course>> {

        val courseRef = db.collection("Course")
        val liveData = MutableLiveData<List<Course>>()

        courseRef.whereArrayContains("studentList", UserManager.userUID!!)
            .addSnapshotListener { snapshot, exception ->

//                Logger.d("addSnapshotListener detect")

                exception?.let {
//                    Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                }

                val list = mutableListOf<Course>()
                for (document in snapshot!!) {
//                    Logger.d(document.id + " => " + document.data)

                    val course = document.toObject(Course::class.java)
                    list.add(course)
                }

                liveData.value = list
            }
        return liveData

    }
}