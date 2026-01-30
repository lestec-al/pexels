package com.lestec.pexels.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Collection(
    val id: String,
    val title: String,
    val description: String? = null,
    val private: Boolean,
    @SerialName("media_count")
    val mediaCount: Int,
    @SerialName("photos_count")
    val photosCount: Int,
    @SerialName("videos_count")
    val videosCount: Int
)

@Serializable
data class Collections(
    val collections: List<Collection>,
    val page: Int,
    @SerialName("per_page")
    val perPage: Int,
    @SerialName("total_results")
    val totalResults: Int,
    @SerialName("next_page")
    val nextPage: String? = null,
    @SerialName("prev_page")
    val prevPage: String? = null
)


@Serializable
data class PhotoSrc(
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
)

@Serializable
data class Photo(
    val id: Long,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    @SerialName("photographer_url")
    val photographerUrl: String,
    @SerialName("photographer_id")
    val photographerId: Long,
    @SerialName("avg_color")
    val avgColor: String,
    val liked: Boolean,
    val alt: String,
    val src: PhotoSrc
)

@Serializable
data class Photos(
    val photos: List<Photo>,
    val page: Int,
    @SerialName("per_page")
    val perPage: Int,
    @SerialName("total_results")
    val totalResults: Int,
    @SerialName("next_page")
    val nextPage: String? = null,
    @SerialName("prev_page")
    val prevPage: String? = null
)