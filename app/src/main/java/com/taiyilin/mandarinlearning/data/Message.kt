package com.taiyilin.mandarinlearning.data

data class Message(

    var senderId: String = "",

    var content: String ="",

    var createdTime: Long? = null,

    var beRead: Boolean? = false

)