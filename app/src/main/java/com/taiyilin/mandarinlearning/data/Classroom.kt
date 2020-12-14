package com.taiyilin.mandarinlearning.data

import android.os.Parcelable
import com.google.android.gms.common.internal.safeparcel.SafeParcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Classroom(

    var id: String = "",

    var courseId: String = "",

    var courseLevel: String = "",

    var courseTitle: String = "",

    var studentId: String = "",

    var teacherId: String = "",

    var answerList: List<Answer>? = null,

    var messageList: List<Message>? =null

): Parcelable


