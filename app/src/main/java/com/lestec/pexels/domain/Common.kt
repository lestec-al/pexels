package com.lestec.pexels.domain

data class Result<out T> (
    val result: T?,
    val errorMsg: String?
)

interface LocalRepo {
    suspend fun getPhotos(): List<Photo>
    suspend fun getPhoto(id: Long): Photo?
    suspend fun savePhoto(photo: Photo)
    suspend fun deletePhoto(id: Long)
}

interface HttpRepo {
    suspend fun getFeaturedCollections(page: Int?, perPage: Int?): Result<Collections>
    suspend fun getCuratedPhotos(page: Int?, perPage: Int?): Result<Photos>
    suspend fun getSearchedPhotos(
        query: String,
        page: Int?,
        perPage: Int?
    ): Result<Photos>
}

interface FileDownloader {
    suspend fun call(url: String)
}

interface Repo {
    suspend fun getPhotos(): List<Photo>
    suspend fun getPhoto(id: Long): Photo?
    suspend fun savePhoto(photo: Photo)
    suspend fun deletePhoto(id: Long)

    suspend fun getFeaturedCollections(page: Int?, perPage: Int?): Result<Collections>
    suspend fun getCuratedPhotos(page: Int?, perPage: Int?): Result<Photos>
    suspend fun getSearchedPhotos(
        query: String,
        page: Int?,
        perPage: Int?
    ): Result<Photos>

    suspend fun downloadFile(url: String)
}