package com.wllpwr.chatchit

import com.google.gson.annotations.SerializedName

data class Message constructor(
    var messageNo: Int = 0,
    var client: String = "",
    var message: String = "",
    var likes: String = "",
    var dislikes: String = "",
    @SerializedName("_id") var id: String = ""
)