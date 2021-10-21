package com.example.gapsi.service

import com.example.gapsi.service.`interface`.WebService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {

    var BASER_URL_CATALG = "https://api.themoviedb.org/3/"

//    private val client = OkHttpClient.Builder().apply {
//
//        addInterceptor(HttpLoggingInterceptor(
//            HttpLoggingInterceptor.Logger { message ->
//                println("LOG-APP: $message")
//            }).apply {
//            level= HttpLoggingInterceptor.Level.BODY
//        })
//
//        addNetworkInterceptor(HttpLoggingInterceptor(
//            HttpLoggingInterceptor.Logger { message ->
//                println("LOG-NET: $message")
//            }).apply {
//            level= HttpLoggingInterceptor.Level.BODY
//        })
//    }.build()



    private val clientToken = OkHttpClient
        .Builder()
        .addInterceptor(WSretrofit())
        .build()

    private val retrofitCatalog = Retrofit.Builder()
        .baseUrl(BASER_URL_CATALG)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        //.client(clientToken)
        .build()
        .create(WebService::class.java)

    fun buildService2(): WebService {
        return retrofitCatalog
    }

}
