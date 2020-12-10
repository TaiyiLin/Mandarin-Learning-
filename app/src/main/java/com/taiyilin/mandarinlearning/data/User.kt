package com.taiyilin.mandarinlearning.data

data class User(

    var id: String = " ",

    var name: String = " ",

    var age: Int? = null,

    var type: String = " ",

    var description: String = " ",

    var rating: Int? = null,

    var level: String = " ",

    var feedbackList: List<Feedback>?= null

)