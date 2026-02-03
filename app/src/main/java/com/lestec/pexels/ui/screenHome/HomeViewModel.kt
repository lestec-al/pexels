package com.lestec.pexels.ui.screenHome

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lestec.pexels.domain.Collection
import com.lestec.pexels.domain.ErrorType
import com.lestec.pexels.domain.Photo
import com.lestec.pexels.domain.Repo
import com.lestec.pexels.domain.Result
import kotlinx.coroutines.launch
import kotlin.collections.plus

class HomeViewModel(private val repo: Repo) : ViewModel() {

    private var page = 1

    private var origFeatured: List<Collection> = emptyList()

    var featured by mutableStateOf(listOf<String>())
        private set

    var isNextPageExist by mutableStateOf(false)
        private set

    var isLoading by mutableStateOf(true)
        private set

    var photos by mutableStateOf(listOf<Photo>())
        private set

    var searchValue by mutableStateOf("")
        private set

    var photosNotFound by mutableStateOf(false)
        private set


    private fun <T> loadingUtil(block: suspend () -> Result<T>) = viewModelScope.launch {
        isLoading = true
        val obj = block()
        isLoading = false
        photosNotFound = obj.errorType == ErrorType.None
    }

    private fun searchPhotos() {
        loadingUtil {
            page = 1
            val obj = repo.getSearchedPhotos(searchValue, page, 30)
            val res = obj.result
            if (res != null) photos = res.photos
            isNextPageExist = res?.nextPage != null
            obj
        }
    }

    private fun loadFeatured() {
        loadingUtil {
            val obj = repo.getFeaturedCollections(null, 7)
            val res = obj.result
            if (res != null) {
                origFeatured = res.collections
                featured = origFeatured.map { it.title }
            }
            obj
        }
    }

    fun loadCuratedPhotos() {
        loadingUtil {
            page = 1
            val obj = repo.getCuratedPhotos(page, 30)
            val res = obj.result
            if (res != null) photos = res.photos
            isNextPageExist = res?.nextPage != null
            obj
        }
    }

    fun selectFeatured(c: String) {
        updateSearchValue(c)
        featured = if (c.isNotEmpty()) {
            searchPhotos()
            listOf(c) + origFeatured.map { it.title }.filter { c != it }
        } else {
            origFeatured.map { it.title }
        }
    }

    fun updateSearchValue(it: String) {
        searchValue = it
    }

    fun onSearch(it: String) {
        if (it.lowercase() in featured.map { it.lowercase() }) {
            selectFeatured(it)
        } else {
            updateSearchValue(it)
            searchPhotos()
        }
    }

    fun addPhotos(isAtBottom: Boolean) {
        if (isAtBottom && photos.isNotEmpty() && isNextPageExist) {
            loadingUtil {
                page++
                val obj = if (searchValue.isEmpty()) {
                    repo.getSearchedPhotos(searchValue, page, 30)
                } else {
                    repo.getCuratedPhotos(page, 30)
                }
                val res = obj.result
                if (res != null) photos += res.photos
                isNextPageExist = res?.nextPage != null
                obj
            }
        }
    }

    fun reloadPhotos() {
        if (photosNotFound) loadCuratedPhotos() else searchPhotos()
    }

    init {
        loadFeatured()
        loadCuratedPhotos()
    }
}