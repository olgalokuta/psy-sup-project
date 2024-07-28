package com.example.psysupapplication.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.reflect.KClass

// Previous URLs
// http://62.3.58.13:8080/api/
// http://localhost:8080/api/
// http://127.0.0.1:8080/api/
// http://10.0.2.2:8080/api/
private const val DEFAULT_API_BASE_URL = "http://localhost:8080/api/"

class ApiProviderImpl(baseUrl: String = DEFAULT_API_BASE_URL) : ApiProvider {
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun <T: Any> provideApi(klass: KClass<T>): T = retrofit.create(klass.java)
}