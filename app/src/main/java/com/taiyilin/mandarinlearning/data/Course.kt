package com.taiyilin.mandarinlearning.data

import android.net.wifi.aware.ParcelablePeerHandle
import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Course(

    var id: String = "",

    var name: String ="",

    var level: String = "",

    var avgRating: String = "",

    var ownerId: String = "",

    var status: Int? = null,

    var image: String = "",

    var updatedTime : Long? = null,

    var description : String = "",

    var feedbackList: List<Feedback>? = null,

    var questionList: List<Question>? = null,

    var studentList: List<String> = mutableListOf()

) : Parcelable

