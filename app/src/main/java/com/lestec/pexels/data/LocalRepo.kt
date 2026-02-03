package com.lestec.pexels.data

import com.lestec.pexels.domain.Photo
import com.lestec.pexels.domain.PhotoSrc

class LocalRepo(private val db: RoomDb) {

    suspend fun getPhotos() = db.dao.getPhotos().map {
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

    suspend fun getPhoto(id: Long): Photo? {
        return db.dao.getPhoto(id).takeIf { it != null }?.let {
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

    suspend fun savePhoto(it: Photo) {
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

    suspend fun deletePhoto(id: Long) {
        db.dao.deletePhoto(id)
    }
}