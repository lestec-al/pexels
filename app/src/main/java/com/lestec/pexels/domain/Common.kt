package com.lestec.pexels.domain

enum class ErrorType { None, Network, Others }

data class Result<out T> (
    val result: T?,
    val errorType: ErrorType
)

interface HttpRepo {
    suspend fun getFeaturedCollections(page: Int?, perPage: Int?): Result<Collections>
    suspend fun getCuratedPhotos(page: Int?, perPage: Int?): Result<Photos>
    suspend fun getSearchedPhotos(
        query: String,
        page: Int?,
        perPage: Int?
    ): Result<Photos>
    suspend fun downloadFile(url: String)
}

interface LocalRepo {
    suspend fun getPhotos(): List<Photo>
    suspend fun getPhoto(id: Long): Photo?
    suspend fun savePhoto(photo: Photo)
    suspend fun deletePhoto(id: Long)
}

data class Repo(
    val http: HttpRepo,
    val local: LocalRepo
)