package com.example.photo_every_day

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitImp {
    fun getRequest(): PictureApi {
        val podRetrofit = Retrofit.Builder()
            .baseUrl("https://api.nasa.gov/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return podRetrofit.create(PictureApi::class.java)
    }
}