package com.example.psysupapplication.api

import kotlin.reflect.KClass

interface ApiProvider {
    fun <T: Any> provideApi(klass: KClass<T>): T
}

internal inline fun <reified T: Any> ApiProvider.provideApi(): T = provideApi(T::class)

val apiProvider: ApiProvider = ApiProviderImpl()