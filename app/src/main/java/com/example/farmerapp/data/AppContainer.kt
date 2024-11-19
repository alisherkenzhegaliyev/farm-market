

package com.example.farmerapp.data

import com.example.farmerapp.service.FarmerMarketApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val farmerMarketRepository: FarmerMarketRepository
}


class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://android-kotlin-fun-mars-server.appspot.com"
    val retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory("application/json".toMediaType()))
            .build()

    val retrofitService: FarmerMarketApiService by lazy {
        retrofit.create(FarmerMarketApiService::class.java)
    }

    override val farmerMarketRepository: FarmerMarketRepository by lazy {
        DefaultFarmerMarketRepository(retrofitService)
    }
}


