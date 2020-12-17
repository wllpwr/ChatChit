package com.wllpwr.chatchit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wllpwr.chatchit.api.ChatApi
import com.wllpwr.chatchit.api.ChitChatResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ChitterGitter {

    private val chatApi: ChatApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.stepoutnyc.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        chatApi = retrofit.create(ChatApi::class.java)

    }

    fun fetchContents(): LiveData<List<Message>> {

        val responseLiveData: MutableLiveData<List<Message>> = MutableLiveData()
        val messageRequest: Call<ChitChatResponse> = chatApi.fetchContents()

        messageRequest.enqueue(object : Callback<ChitChatResponse> {
            override fun onFailure(call: Call<ChitChatResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch messages: $t")
            }

            override fun onResponse(
                call: Call<ChitChatResponse>,
                response: Response<ChitChatResponse>
            ) {
                Log.d(TAG, "Response received: ${response.body()}")
                val messageResponse: ChitChatResponse? = response.body()
                val chatItems: List<Message> = messageResponse?.messageItems
                    ?: mutableListOf()
                responseLiveData.value = chatItems
            }
        })


        return responseLiveData
    }

    fun postMessage(string: String) {
        val messagePost: Call<ResponseBody> = chatApi.postMessage(string)

        messagePost.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(TAG, "Failed to send message: $t")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d(TAG, "Server responded with: ${response.body()}")
            }
        })

    }

    fun likeContents(id: String) {
        val messagePost: Call<ResponseBody> = chatApi.likeMessage(id)

        messagePost.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(TAG, "Failed to send message: $t")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d(TAG, "Server responded with: ${response.body()}")
            }
        })
    }

    fun dislikeContents(id: String) {
        val messagePost: Call<ResponseBody> = chatApi.dislikeMessage(id)

        messagePost.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(TAG, "Failed to send message: $t")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d(TAG, "Server responded with: ${response.body()}")
            }
        })

    }
}

