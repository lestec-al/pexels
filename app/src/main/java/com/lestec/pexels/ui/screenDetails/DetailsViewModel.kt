package com.lestec.pexels.ui.screenDetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lestec.pexels.domain.FileDownloader
import com.lestec.pexels.domain.Photo
import com.lestec.pexels.domain.Repo
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val repo: Repo,
    private val fileDownloader: FileDownloader
) : ViewModel() {

    var isPhotoSaved by mutableStateOf(false)
        private set

    fun downloadPhoto(photo: Photo) = viewModelScope.launch {
        fileDownloader.call(photo.src.original)
    }

    fun addOrDelBookmark(photo: Photo) = viewModelScope.launch {
        if (isPhotoSaved) {
            repo.deletePhoto(photo.id)
        } else {
            repo.savePhoto(photo)
        }
        getIfPhotoIsSaved(photo)
    }

    fun getIfPhotoIsSaved(photo: Photo) = viewModelScope.launch {
        isPhotoSaved = repo.getPhoto(photo.id) != null
    }
}