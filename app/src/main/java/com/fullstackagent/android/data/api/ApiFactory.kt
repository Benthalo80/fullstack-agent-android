package com.fullstackagent.android.data.api

import com.fullstackagent.android.BuildConfig
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object ApiFactory {
    fun create(): DesignArenaApi {
        val json = Json { ignoreUnknownKeys = true; explicitNulls = false }
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val req = chain.request().newBuilder()
                val key = BuildConfig.DESIGN_ARENA_KEY
                if (key.isNotBlank()) req.addHeader("Authorization", "Bearer $key")
                chain.proceed(req.build())
            }
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .build()
        return Retrofit.Builder()
            .baseUrl(DesignArenaApi.BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(DesignArenaApi::class.java)
    }
}
