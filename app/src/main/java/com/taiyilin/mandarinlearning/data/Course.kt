package com.taiyilin.mandarinlearning.data

import com.google.firebase.Timestamp

data class Course(

    var id: String = "",

    var name: String = "",

    var level: String = "",

    var rating: String = "",

    var ownerId: String = "",

    var status: String = "",

    var updatedTime : Long?= null

)

