package com.lestec.pexels.ui.screen_home

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lestec.pexels.domain.Collection
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

    val featuredListState = LazyListState()

    val photosGridState = LazyStaggeredGridState()


    private fun <T> loadingUtil(block: suspend () -> Result<T>) = viewModelScope.launch {
        isLoading = true
        val obj = block()
        isLoading = false
        photosNotFound = obj.errorMsg == null
    }

    private fun resetScrollStates() {
        if (featured.isNotEmpty()) {
            viewModelScope.launch { featuredListState.scrollToItem(0) }
        }
        if (photos.isNotEmpty()) {
            viewModelScope.launch { photosGridState.scrollToItem(0) }
        }
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

    private fun loadCuratedPhotos() {
        loadingUtil {
            page = 1
            val obj = repo.getCuratedPhotos(page, 30)
            val res = obj.result
            if (res != null) photos = res.photos
            isNextPageExist = res?.nextPage != null
            obj
        }
    }

    fun updateSearchValue(it: String) {
        searchValue = it
    }

    fun selectFeatured(c: String) {
        updateSearchValue(c)
        featured = if (c.isNotEmpty()) {
            searchPhotos()
            listOf(c) + origFeatured.map { it.title }.filter { c != it }
        } else {
            origFeatured.map { it.title }
        }
        resetScrollStates()
    }

    fun onSearch(it: String) {
        if (it.lowercase() in featured.map { it.lowercase() }) {
            selectFeatured(it)
        } else {
            updateSearchValue(it)
            searchPhotos()
            resetScrollStates()
        }
    }

    fun addPhotos(isAtBottom: Boolean) {
        if (isAtBottom && photos.isNotEmpty() && isNextPageExist && !isLoading) {
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
        resetScrollStates()
    }

    init {
        loadFeatured()
        loadCuratedPhotos()
    }
}