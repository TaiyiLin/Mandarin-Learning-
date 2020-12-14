package com.taiyilin.mandarinlearning.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Feedback(

    var courseId: String = "",

    var content: String = "",

    var rating: Int? = null,

    var senderId: String = ""

) :Parcelable

