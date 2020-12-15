package com.wllpwr.chatchit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wllpwr.chatchit.api.ChatApi
import com.wllpwr.chatchit.api.MessageResponse
import com.wllpwr.chatchit.api.ChatResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChitterGitter {

    private val chatApi: ChatApi

    init{
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.stepoutnyc.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        chatApi = retrofit.create(ChatApi::class.java)

    }

    fun fetchContents(): LiveData<List<Message>> {

        val responseLiveData: MutableLiveData<List<Message>> = MutableLiveData()
        val messageRequest: Call<MessageResponse> = chatApi.fetchContents()

        messageRequest.enqueue(object : Callback<MessageResponse> {
            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch messages", t)
            }

            override fun onResponse(
                call: Call<MessageResponse>,
                response: Response<MessageResponse>
            ) {
                Log.d(TAG, "Response received: ${response.body()}")
                val messageResponse: MessageResponse? = response.body()
                val chatResponse: ChatResponse? = messageResponse?.messages
                val chatItems: List<Message> = chatResponse?.messageItems
                    ?: mutableListOf()
                responseLiveData.value = chatItems
            }
        })

        return responseLiveData
    }
}

