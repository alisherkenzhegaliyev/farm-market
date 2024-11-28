

package com.example.farmerapp.data

import com.example.farmerapp.service.FarmerMarketApiService
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val farmerMarketRepository: FarmerMarketRepository
}


class DefaultAppContainer : AppContainer {
    private val baseUrl = "http://10.101.29.34:8000/"

//    val loggingInterceptor = HttpLoggingInterceptor()
//    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//
//    val client = OkHttpClient.Builder()
//        .addInterceptor {
//
//        }
//        .build()


    val retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val retrofitService: FarmerMarketApiService by lazy {
        retrofit.create(FarmerMarketApiService::class.java)
    }

    override val farmerMarketRepository: FarmerMarketRepository by lazy {
        DefaultFarmerMarketRepository(retrofitService)
    }
}


