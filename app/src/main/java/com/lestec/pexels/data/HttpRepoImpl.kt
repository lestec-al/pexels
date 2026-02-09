package com.lestec.pexels.data

import com.lestec.pexels.domain.Collections
import com.lestec.pexels.domain.HttpRepo
import com.lestec.pexels.domain.Photo
import com.lestec.pexels.domain.Photos
import com.lestec.pexels.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess

class HttpRepoImpl(private val client: HttpClient): HttpRepo {

    private suspend inline fun <reified T> makeRequest(block: () -> HttpResponse?): Result<T> {
        return try {
            val res = block()
            if (res!!.status.isSuccess()) {
                Result(res.body<T>(), null)
            } else {
                Result(null, "API error happened: ${res.status.description}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result(null, "API error happened: ${e.message}")
        }
    }

    override suspend fun getFeaturedCollections(
        page: Int?,
        perPage: Int?
    ): Result<Collections> {
        return makeRequest {
            client.get(Consts.FEATURED) {
                header(HttpHeaders.Authorization, Consts.API_KEY)
                url {
                    if (page != null) parameters.append("page", "$page")
                    if (perPage != null) parameters.append("per_page", "$perPage")
                }
            }
        }
    }

    override suspend fun getCuratedPhotos(
        page: Int?,
        perPage: Int?
    ): Result<Photos> {
        return makeRequest {
            client.get(Consts.CURATED) {
                header(HttpHeaders.Authorization, Consts.API_KEY)
                url {
                    if (page != null) parameters.append("page", "$page")
                    if (perPage != null) parameters.append("per_page", "$perPage")
                }
            }
        }
    }

    override suspend fun getSearchedPhotos(
        query: String,
        page: Int?,
        perPage: Int?
    ): Result<Photos> {
        return makeRequest {
            client.get(Consts.SEARCH) {
                header(HttpHeaders.Authorization, Consts.API_KEY)
                url {
                    parameters.append("query", query)
                    if (page != null) parameters.append("page", "$page")
                    if (perPage != null) parameters.append("per_page", "$perPage")
                }
            }
        }
    }

    override suspend fun getPhoto(id: Long): Result<Photo> {
        return makeRequest {
            client.get(Consts.getPhotoAddress(id)) {
                header(HttpHeaders.Authorization, Consts.API_KEY)
            }
        }
    }
}