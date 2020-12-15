package com.wllpwr.chatchit.api

import com.google.gson.annotations.SerializedName
import com.wllpwr.chatchit.Message

class ChatResponse {
    @SerializedName("messages")
    lateinit var messageItems: List<Message>

}