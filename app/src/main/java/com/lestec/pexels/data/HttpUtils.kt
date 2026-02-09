package com.lestec.pexels.data

import com.lestec.pexels.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json

object KtorClientProvider {
    val client = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }
}

object Consts {
    const val API_KEY: String = BuildConfig.API_KEY
    const val BASE_URL = "https://api.pexels.com/v1"
    const val FEATURED = "$BASE_URL/collections/featured"
    const val CURATED = "$BASE_URL/curated"
    const val SEARCH = "$BASE_URL/search"
    fun getPhotoAddress(id: Long) = "$BASE_URL/photos/$id"
}