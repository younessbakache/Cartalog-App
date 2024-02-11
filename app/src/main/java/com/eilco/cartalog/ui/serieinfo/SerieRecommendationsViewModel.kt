package com.vitor238.cartalog.ui.serieinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vitor238.cartalog.data.model.SerieRecommendation
import com.vitor238.cartalog.data.repository.CARAPIRepository
import com.vitor238.cartalog.utils.ApiStatus
import kotlinx.coroutines.launch

class SerieRecommendationsViewModel : ViewModel() {
    private val tmdbRepository = CARAPIRepository()

    private var _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private var _serieRecommendations = MutableLiveData<List<SerieRecommendation>>()
    val serieRecommendation: LiveData<List<SerieRecommendation>>
        get() = _serieRecommendations

    fun getRecommendations(serieId: Int) {
        viewModelScope.launch {
            val result = kotlin.runCatching { tmdbRepository.getSerieRecommendations(serieId) }
            _status.value = ApiStatus.LOADING
            result.onSuccess {
                _status.value = ApiStatus.DONE
                _serieRecommendations.value = it
            }.onFailure {
                _status.value = ApiStatus.ERROR
            }
        }
    }
}
