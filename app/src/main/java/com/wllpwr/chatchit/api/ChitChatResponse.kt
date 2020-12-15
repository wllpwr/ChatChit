package com.wllpwr.chatchit.api

import com.google.gson.annotations.SerializedName
import com.wllpwr.chatchit.Message

class ChitChatResponse {
    @SerializedName("messages")
    lateinit var messageItems: List<Message>

}