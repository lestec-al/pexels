package com.lestec.pexels.ui.screenBookmarks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lestec.pexels.domain.Photo
import com.lestec.pexels.domain.Repo
import kotlinx.coroutines.launch

class BookmarksViewModel(private val repo: Repo) : ViewModel() {

    var localPhotos by mutableStateOf(listOf<Photo>())
        private set

    fun loadLocalPhotos() = viewModelScope.launch {
        localPhotos = repo.getLocalPhotos()
    }
}