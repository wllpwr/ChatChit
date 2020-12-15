package com.wllpwr.chatchit

import androidx.lifecycle.ViewModel

class ChatListViewModel : ViewModel() {

    val messages = mutableListOf<Message>()

    init {
        for (i in 0 until 20) {
            val message = Message()
            message.messageNo = i
            messages += message
        }

    }

}