package com.vitor238.cartalog.ui.home.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vitor238.cartalog.data.model.MediaSearch
import com.vitor238.cartalog.data.repository.CARAPIRepository
import com.vitor238.cartalog.utils.SearchStatus
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val tmdbRepository = CARAPIRepository()
    private val _status = MutableLiveData<SearchStatus>()
    val status: LiveData<SearchStatus>
        get() = _status

    private var _searchList = MutableLiveData<List<MediaSearch>>()
    val searchList: LiveData<List<MediaSearch>>
        get() = _searchList


    fun searchMovieOrSeries(query: String) {
        _status.value = SearchStatus.EMPTY
        viewModelScope.launch {
            val result = kotlin.runCatching { tmdbRepository.searchMoviesOrSeries(query) }
            result.onSuccess { list ->

                _searchList.value = list.filterNot { it.mediaType == "person" }
                if (list.isEmpty()) {
                    _status.value = SearchStatus.NO_RESULTS
                } else {
                    _status.value = SearchStatus.DONE
                }
            }.onFailure {
                _status.value = SearchStatus.ERROR
            }
        }
    }

}