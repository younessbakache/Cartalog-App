package com.vitor238.cartalog.ui.home.home.brands

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vitor238.cartalog.data.model.PopularSerie
import com.vitor238.cartalog.data.repository.CARAPIRepository
import com.vitor238.cartalog.utils.ApiStatus
import kotlinx.coroutines.launch

class PopularSeriesViewModel : ViewModel() {
    private val tmdbRepository = CARAPIRepository()

    private var _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private var _popularTVSeries = MutableLiveData<List<PopularSerie>>()
    val popularTVSeries: LiveData<List<PopularSerie>>
        get() = _popularTVSeries

    private fun getPopularTVSeries() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            val result = kotlin.runCatching { tmdbRepository.getPopularTVSeries() }
            result.onSuccess {
                _status.value = ApiStatus.DONE
                _popularTVSeries.value = it
            }.onFailure {
                _status.value = ApiStatus.ERROR
            }
        }
    }

    init {
        getPopularTVSeries()
    }
}