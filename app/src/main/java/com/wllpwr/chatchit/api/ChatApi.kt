package com.wllpwr.chatchit.api

import retrofit2.Call
import retrofit2.http.GET

interface ChatApi {

    @GET("chitchat" +
        "?client=william.gesler@mymail.champlain.edu" +
        "&key=c16e71b1-377d-4bf2-bf06-65a18198fe58"

    )
    fun fetchContents(): Call<MessageResponse>
}