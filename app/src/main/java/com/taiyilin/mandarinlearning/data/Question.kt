package com.taiyilin.mandarinlearning.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Question(

    var number: Int? = null,

    var title: String = " ",

    var type: String = " ",

    var answer: String = " "

): Parcelable