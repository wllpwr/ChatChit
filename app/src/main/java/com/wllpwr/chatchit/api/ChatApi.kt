package com.wllpwr.chatchit.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

const val CLIENT = "?client=william.gesler@mymail.champlain.edu"
const val API_KEY = "&key=c16e71b1-377d-4bf2-bf06-65a18198fe58"

interface ChatApi {

    @GET(
        "chitchat" +
                CLIENT +
                API_KEY
    )
    fun fetchContents(): Call<ChitChatResponse>

    @POST(
        "chitchat" +
                CLIENT +
                API_KEY
    )
    fun postMessage(@Query("message") message: String): Call<ResponseBody>

    @GET(
        "chitchat/like/{id}" +
                CLIENT +
                API_KEY
    )
    fun likeMessage(@Path("id") id: String): Call<ResponseBody>

    @GET(
        "chitchat/dislike/{id}" +
                CLIENT +
                API_KEY
    )
    fun dislikeMessage(@Path("id") id: String): Call<ResponseBody>
}