package com.wllpwr.chatchit

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class ChatListViewModel : ViewModel() {
    val messageLiveData: LiveData<List<Message>> = ChitterGitter().fetchContents()

    fun postContents(string: String) {
        ChitterGitter().postMessage(string)
    }

    fun likeContents(id: String) {
        ChitterGitter().likeContents(id)
    }

    fun dislikeContents(id: String) {
        ChitterGitter().dislikeContents(id)
    }
}