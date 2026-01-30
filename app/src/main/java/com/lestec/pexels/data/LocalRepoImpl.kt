package com.lestec.pexels.data

import com.lestec.pexels.domain.LocalRepo
import com.lestec.pexels.domain.Photo
import com.lestec.pexels.domain.PhotoSrc

class LocalRepoImpl(private val db: RoomDb): LocalRepo {

    override suspend fun getPhotos() = db.dao.getPhotos().map {
        Photo(
            id = it.id,
            width = it.width,
            height = it.height,
            url = it.url,
            photographer = it.photographer,
            photographerUrl = it.photographerUrl,
            photographerId = it.photographerId,
            avgColor = it.avgColor,
            liked = it.liked,
            alt = it.alt,
            src = PhotoSrc(
                original = it.original,
                large2x = it.large2x,
                large = it.large,
                medium = it.medium,
                small = it.small,
                portrait = it.portrait,
                landscape = it.landscape,
                tiny = it.tiny
            )
        )
    }

    override suspend fun getPhoto(id: Long): Photo? {
        val res = db.dao.getPhoto(id)
        return if (res.isEmpty()) null else {
            val it = res[0]
            Photo(
                id = it.id,
                width = it.width,
                height = it.height,
                url = it.url,
                photographer = it.photographer,
                photographerUrl = it.photographerUrl,
                photographerId = it.photographerId,
                avgColor = it.avgColor,
                liked = it.liked,
                alt = it.alt,
                src = PhotoSrc(
                    original = it.original,
                    large2x = it.large2x,
                    large = it.large,
                    medium = it.medium,
                    small = it.small,
                    portrait = it.portrait,
                    landscape = it.landscape,
                    tiny = it.tiny
                )
            )
        }
    }

    override suspend fun savePhoto(it: Photo) {
        val photo = PhotoLocal(
            id = it.id,
            width = it.width,
            height = it.height,
            url = it.url,
            photographer = it.photographer,
            photographerUrl = it.photographerUrl,
            photographerId = it.photographerId,
            avgColor = it.avgColor,
            liked = it.liked,
            alt = it.alt,
            original = it.src.original,
            large2x = it.src.large2x,
            large = it.src.large,
            medium = it.src.medium,
            small = it.src.small,
            portrait = it.src.portrait,
            landscape = it.src.landscape,
            tiny = it.src.tiny
        )
        db.dao.savePhoto(photo)
    }

    override suspend fun deletePhoto(id: Long) {
        db.dao.deletePhoto(id)
    }
}