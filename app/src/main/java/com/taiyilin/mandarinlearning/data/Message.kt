package com.taiyilin.mandarinlearning.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Message(

    var senderId: String = "",

    var receiverId: String= "",

    var content: String = "",

    var createdTime: Long = -1,

    var beRead: Boolean? = false

): Parcelable