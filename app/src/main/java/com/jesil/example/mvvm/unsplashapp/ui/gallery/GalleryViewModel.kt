package com.jesil.example.mvvm.unsplashapp.ui.gallery

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.jesil.example.mvvm.unsplashapp.constant.Constants.UNSPLASH_DEFAULT_QUERY
import com.jesil.example.mvvm.unsplashapp.data.UnsplashRepository

class GalleryViewModel @ViewModelInject constructor(
    private val repository: UnsplashRepository
) : ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    val photos = currentQuery.switchMap { queryString->
        repository.getSearchResult(queryString).cachedIn(viewModelScope)
    }

    fun searchPhotos(query: String){
        currentQuery.value = query
    }

    companion object {
        private const val DEFAULT_QUERY = UNSPLASH_DEFAULT_QUERY
    }

}