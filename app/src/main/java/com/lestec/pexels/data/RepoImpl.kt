package com.lestec.pexels.data

import com.lestec.pexels.domain.Collections
import com.lestec.pexels.domain.Photo
import com.lestec.pexels.domain.Photos
import com.lestec.pexels.domain.Repo
import com.lestec.pexels.domain.Result

class RepoImpl(
    private val http: HttpRepo,
    private val local: LocalRepo
): Repo {
    override suspend fun getPhotos(): List<Photo> = local.getPhotos()

    override suspend fun getPhoto(id: Long): Photo? = local.getPhoto(id)

    override suspend fun savePhoto(photo: Photo) = local.savePhoto(photo)

    override suspend fun deletePhoto(id: Long) = local.deletePhoto(id)

    override suspend fun getFeaturedCollections(
        page: Int?,
        perPage: Int?
    ): Result<Collections> = http.getFeaturedCollections(page, perPage)

    override suspend fun getCuratedPhotos(
        page: Int?,
        perPage: Int?
    ): Result<Photos> = http.getCuratedPhotos(page, perPage)

    override suspend fun getSearchedPhotos(
        query: String,
        page: Int?,
        perPage: Int?
    ): Result<Photos> = http.getSearchedPhotos(query, page, perPage)
}