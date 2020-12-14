package com.taiyilin.mandarinlearning.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Answer(

    var questionNumber: Int? = null,

    var answer: String = "",

    var type: String = ""

):Parcelable


