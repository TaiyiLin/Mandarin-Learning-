package com.taiyilin.mandarinlearning

data class Feedback(

    var courseId: String = "",

    var content: String = "",

    var rating: Int? = null,

    var senderId: String = ""

)