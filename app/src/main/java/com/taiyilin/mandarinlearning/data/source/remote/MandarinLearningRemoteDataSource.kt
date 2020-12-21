package com.taiyilin.mandarinlearning.data.source.remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.taiyilin.mandarinlearning.data.*
import com.taiyilin.mandarinlearning.data.source.MandarinLearningDataSource
import com.taiyilin.mandarinlearning.login.UserManager
import com.taiyilin.mandarinlearning.util.Logger
import java.util.*
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
                    continuation.resume(Result.Fail(""))
                }
            }
    }


    override suspend fun addSelectedCourse(classroom: Classroom): Result<Boolean> =
        suspendCoroutine { continuation ->

            val classroomRef = FirebaseFirestore.getInstance().collection("Classroom")
            //val document的document 是一個空資料夾
            val document = classroomRef.document()

            classroom.id = document.id

            document
                .set(classroom)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Logger.i("ToneGo: $classroom")

                        continuation.resume(Result.Success(true))
                    } else {
                        task.exception?.let {

                            Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result.Fail(""))
                    }
                }

        }


    override suspend fun updateCourse(
        courseId: String, studentId: String
    ): Result<Boolean> = suspendCoroutine { continuation ->

        val courseRef = FirebaseFirestore.getInstance().collection("Course")

        courseRef.document(courseId)
            .update("studentList", FieldValue.arrayUnion(studentId))
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Logger.i("Course: $courseId")

                    continuation.resume(Result.Success(true))
                } else {
                    task.exception?.let {

                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
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

                Logger.i("addSnapshotListener detect")

                exception?.let {
                    Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                }

                val list = mutableListOf<Course>()
                for (document in snapshot!!) {
                    Logger.d(document.id + " => " + document.data)

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

                Logger.d("addSnapshotListener detect")

                exception?.let {
                    Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                }

                val list = mutableListOf<Course>()
                for (document in snapshot!!) {
                    Logger.d(document.id + " => " + document.data)

                    val course = document.toObject(Course::class.java)
                    list.add(course)
                }

                liveData.value = list
            }

        return liveData

    }

    override suspend fun getAllClassrooms(): Result<List<Classroom>> =
        suspendCoroutine { continuation ->

            db.collection("Classroom")
//            .orderBy(KEY_CREATED_TIME, Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        val list = mutableListOf<Classroom>()
                        for (document in task.result!!) {
                            Log.d("Taiyi", document.id + " => " + document.data)

                            val classroom = document.toObject(Classroom::class.java)

                            list.add(classroom)
                        }
                        continuation.resume(Result.Success(list))
                    } else {
                        task.exception?.let {

                            Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result.Fail(""))
                    }
                }
        }

    override fun getLiveClassrooms(): MutableLiveData<List<Classroom>> {
        val liveData = MutableLiveData<List<Classroom>>()

        db.collection("Classroom")
//            .orderBy(KEY_CREATED_TIME, Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, exception ->

                Logger.i("addSnapshotListener detect")

                exception?.let {
                    Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                }

                val list = mutableListOf<Classroom>()
                for (document in snapshot!!) {
                    Logger.d(document.id + " => " + document.data)

                    val classroom = document.toObject(Classroom::class.java)
                    list.add(classroom)
                }

                liveData.value = list
            }
        return liveData

    }


    override suspend fun getQuestions(classroom: Classroom): Result<List<Question>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection("Course").document(classroom.courseId).collection("Question")
                .orderBy("number", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val list = mutableListOf<Question>()
                        for (document in task.result!!) {
                            Logger.d(document.id + " => " + document.data)

                            val question = document.toObject(Question::class.java)
                            list.add(question)
                        }
                        continuation.resume(Result.Success(list))
                    } else {
                        task.exception?.let {

                            Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result.Fail(""))
                    }
                }
        }

    override fun getAllLiveMessages(classroom: Classroom): MutableLiveData<List<Message>> {
        val liveData = MutableLiveData<List<Message>>()

        db.collection("Classroom").document(classroom.id).collection("Message")
            .orderBy("createdTime", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, exception ->

                Logger.i("addSnapshotListener detect")

                exception?.let {
                    Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                }

                val list = mutableListOf<Message>()
                for (document in snapshot!!) {
                    Logger.d(document.id + " => " + document.data)

                    val message = document.toObject(Message::class.java)
                    list.add(message)
                }

                liveData.value = list
            }
        return liveData

    }

    //Input answer in Classroom detail page
    override suspend fun sendAnswer(classroom: Classroom, answer: Answer): Result<Answer> =
        suspendCoroutine { continuation ->
            //拿到一個Classroom的一包answers
            val answers = db.collection("Classroom")
                .document(classroom.id)
                .collection("Answer")
                .whereEqualTo(
                    "questionNumber",
                    answer.questionNumber
                ) //whereEqualTo 是找到那個collection下的一個欄位
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Logger.i("ToneGO: $answer")

                        //一個list 但裡面只有一筆document
                        for (document in task.result!!) {

                            document.id
                            db.collection("Classroom").document(classroom.id).collection("Answer")
                                .document(document.id)
                                .set(answer)
                                .addOnCompleteListener { task2 ->
                                    if (task2.isSuccessful) {
                                        Logger.i("ToneGO: $answer")

                                        continuation.resume(Result.Success(answer))
                                    } else {
                                        task2.exception?.let {

                                            Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                                            continuation.resume(Result.Error(it))

                                        }
                                        continuation.resume(Result.Fail(""))
                                    }
                                }

                        }


                    }
                }
        }



    override suspend fun getFeedback(course: Course): Result<List<Feedback>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection("Course").document(course.id).collection("Feedback")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val list = mutableListOf<Feedback>()
                        for (document in task.result!!) {
                            Logger.d(document.id + " => " + document.data)

                            val feedback = document.toObject(Feedback::class.java)
                            list.add(feedback)
                        }
                        continuation.resume(Result.Success(list))
                    } else {
                        task.exception?.let {

                            Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result.Fail(""))
                    }
                }
        }

    override suspend fun sendMessage(classroom: Classroom, message: Message): Result<Boolean> =
        suspendCoroutine { continuation ->

            val msgRef = FirebaseFirestore.getInstance().collection("Classroom").document(classroom.id).collection("Message")
            //val document的document 是一個空資料夾
            val document = msgRef.document()

            message.createdTime = Calendar.getInstance().timeInMillis

            document
                .set(message)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Logger.i("ToneGo: $message")

                        continuation.resume(Result.Success(true))
                    } else {
                        task.exception?.let {

                            Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result.Fail(""))
                    }
                }

        }

//    //ToDo
//    override suspend fun getAnswerOutput(): Result<Answer> {
//        TODO("Not yet implemented")
//    }


//    //Get Answer output in Classroom detail page
//    override suspend fun getAnswerOutput(classroom: Classroom, answer: Answer): Result<Answer> = suspendCoroutine { continuation ->
//        //拿到一個Classroom的一包answers
//        val answers = db.collection("Classroom")
//            .document(classroom.id)
//            .collection("Answer")
//            .whereEqualTo(
//                "questionNumber",
//                answer.questionNumber
//            ) //whereEqualTo 是找到那個collection下的一個欄位
//            .get()
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Logger.i("ToneGO: $answer")
//
//                    //一個list 但裡面只有一筆document
//                    for (document in task.result!!) {
//
//
//
//                    }
//
//                        continuation.resume(Result.Success(answer))
//                    } else {
//                        task.exception?.let {
//
//                            Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
//                            continuation.resume(Result.Error(it))
//
//                        }
//                        continuation.resume(Result.Fail(""))
//                    }
//                }
//            }
//    }

    override fun getLiveAnswer(classroom: Classroom): MutableLiveData<List<Answer>> {
        val liveData = MutableLiveData<List<Answer>>()

        db.collection("Classroom").document(classroom.id).collection("Answer")
//            .orderBy("createdTime", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, exception ->

                Logger.i("addSnapshotListener detect")

                exception?.let {
                    Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                }

                val list = mutableListOf<Answer>()
                for (document in snapshot!!) {
                    Logger.d(document.id + " => " + document.data)

                    val answer = document.toObject(Answer::class.java)
                    list.add(answer)
                }

                liveData.value = list
            }
        return liveData

    }


}
