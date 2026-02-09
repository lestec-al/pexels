package com.lestec.pexels.ui.screenDetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lestec.pexels.domain.Photo
import com.lestec.pexels.domain.Repo
import kotlinx.coroutines.launch

class DetailsViewModel(private val repo: Repo) : ViewModel() {

    var isPhotoSaved by mutableStateOf(false)
        private set

    var photo by mutableStateOf<Photo?>(null)
        private set

    var isLoading by mutableStateOf(true)
        private set

    fun downloadPhoto() = viewModelScope.launch {
        if (photo != null) {
            repo.downloadFile(photo!!.src.original)
        }
    }

    fun addOrDelBookmark() = viewModelScope.launch {
        if (photo != null) {
            if (isPhotoSaved) repo.deletePhoto(photo!!.id) else repo.savePhoto(photo!!)
            getIfPhotoIsSaved(photo!!.id)
        }
    }

    fun initData(photoId: Long) {
        photo = null
        viewModelScope.launch {
            isLoading = true
            photo = repo.getLocalPhoto(photoId) ?: repo.getWebPhoto(photoId).result
            getIfPhotoIsSaved(photoId)
            isLoading = false
        }
    }

    private suspend fun getIfPhotoIsSaved(photoId: Long) {
        isPhotoSaved = repo.getLocalPhoto(photoId) != null
    }
}