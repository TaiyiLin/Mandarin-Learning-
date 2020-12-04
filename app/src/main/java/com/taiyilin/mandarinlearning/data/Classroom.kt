package com.taiyilin.mandarinlearning.data

data class Classroom(

    var id: String = "",

    var courseId: String = "",

    var studentId: String = "",

    var teacherId: String = "",

    var answerList: List<Answer>? = null,

    var messageList: List<Message>? =null
)