package com.example.libraries2.retrofit

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApi {

    @GET("/v1/images/search")
    fun search(@Query("limit") limit: Int?=3): Call<List<Cat>>

    companion object {
        private var instance: CatApi? = null

        fun getInstance() : CatApi {
            if(instance==null) {
                var retrofit = Retrofit.Builder()
                    .baseUrl("https://api.thecatapi.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                instance = retrofit.create(CatApi::class.java)
            }
            return instance!!
        }
    }

}