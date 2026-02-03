package com.lestec.pexels.data

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Room
import androidx.room.Upsert

@Entity
data class PhotoLocal(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "width") val width: Int,
    @ColumnInfo(name = "height") val height: Int,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "photographer") val photographer: String,
    @ColumnInfo(name = "photographerUrl") val photographerUrl: String,
    @ColumnInfo(name = "photographerId") val photographerId: Long,
    @ColumnInfo(name = "avgColor") val avgColor: String,
    @ColumnInfo(name = "liked") val liked: Boolean,
    @ColumnInfo(name = "alt") val alt: String,
    @ColumnInfo(name = "original") val original: String,
    @ColumnInfo(name = "large2x") val large2x: String,
    @ColumnInfo(name = "large") val large: String,
    @ColumnInfo(name = "medium") val medium: String,
    @ColumnInfo(name = "small") val small: String,
    @ColumnInfo(name = "portrait") val portrait: String,
    @ColumnInfo(name = "landscape") val landscape: String,
    @ColumnInfo(name = "tiny") val tiny: String
)

@Dao
interface PhotosDao {
    @Query("SELECT * FROM PhotoLocal")
    suspend fun getPhotos(): List<PhotoLocal>

    @Query("SELECT * FROM PhotoLocal WHERE id = :id")
    suspend fun getPhoto(id: Long): PhotoLocal?

    @Upsert
    suspend fun savePhoto(photo: PhotoLocal)

    @Query("DELETE FROM PhotoLocal WHERE id = :id")
    suspend fun deletePhoto(id: Long)
}

@Database(entities = [PhotoLocal::class], version = 1)
abstract class RoomDb : RoomDatabase() {
    abstract val dao: PhotosDao
}

fun getRoomClient(context: Context) = Room.databaseBuilder(
    context,
    RoomDb::class.java,
    "pexels.db"
).build()