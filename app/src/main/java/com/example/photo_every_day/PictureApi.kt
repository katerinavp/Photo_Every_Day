package com.example.photo_every_day

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureApi {
    @GET("planetary/apod")
    fun getPicture(@Query("api_key") apiKey: String): Call<Data>
}