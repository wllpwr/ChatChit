package com.wllpwr.chatchit

data class Message constructor(
        var messageNo: Int = 0,
        var client: String = "",
        var message: String = "",
        var likes: Int = 0,
        var dislikes: Int = 0,


)