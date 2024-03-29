package com.vitor238.cartalog.ui.home.home.trends

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vitor238.cartalog.data.model.Trend
import com.vitor238.cartalog.data.repository.CARAPIRepository
import com.vitor238.cartalog.utils.ApiStatus
import kotlinx.coroutines.launch

class TrendsViewModel : ViewModel() {
    private val tmdbRepository = CARAPIRepository()
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status
    private val _trends = MutableLiveData<List<Trend>>()
    val trends: LiveData<List<Trend>>
        get() = _trends

    private fun getTrends() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            val result = kotlin.runCatching { tmdbRepository.getTrendingMoviesAndSeries() }
            result.onSuccess {
                _status.value = ApiStatus.DONE
                _trends.value = it
            }.onFailure {
                _status.value = ApiStatus.ERROR
            }
        }
    }

    init {
        getTrends()
    }
}
