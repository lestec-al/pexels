package com.lestec.pexels.data

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import com.lestec.pexels.domain.FileDownloader

class FileDownloaderImpl(private val context: Context): FileDownloader {
    override suspend fun call(url: String) {
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