package com.lestec.pexels.data

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import com.lestec.pexels.domain.Photos
import com.lestec.pexels.domain.ErrorType
import com.lestec.pexels.domain.Collections
import com.lestec.pexels.domain.HttpRepo
import com.lestec.pexels.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess
import kotlinx.io.IOException
import androidx.core.net.toUri

class HttpRepoImpl(
    private val client: HttpClient,
    private val context: Context
) : HttpRepo {

    private suspend inline fun <reified T> makeRequest(block: () -> HttpResponse?): Result<T> {
        return try {
            val res = block()
            if (res!!.status.isSuccess()) {
                Result(res.body<T>(), ErrorType.None)
            } else {
                Result(null, ErrorType.Others)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Result(null, ErrorType.Network)
        } catch (e: HttpRequestTimeoutException) {
            e.printStackTrace()
            Result(null, ErrorType.Network)
        } catch (e: Exception) {
            e.printStackTrace()
            Result(null, ErrorType.Others)
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

    override suspend fun downloadFile(url: String) {
        val fileName = url.substringAfterLast("/")
        val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
        val request = DownloadManager.Request(url.toUri())
        request
            .setTitle(fileName)
            .setNotificationVisibility(
                DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
            )
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)
            .setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE
            )
            .setRequiresCharging(false)
            .setMimeType(
                when {
                    url.endsWith(".jpg") || url.endsWith(".jpeg") -> "image/jpeg"
                    url.endsWith(".png") -> "image/png"
                    url.endsWith(".mp4") -> "video/mp4"
                    url.endsWith(".mp3") -> "audio/mpeg"
                    else -> "*/*"
                }
            )
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                fileName
            )
        manager?.enqueue(request)
    }
}