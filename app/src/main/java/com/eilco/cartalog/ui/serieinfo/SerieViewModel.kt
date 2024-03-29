package com.vitor238.cartalog.ui.serieinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vitor238.cartalog.data.model.serie.Serie
import com.vitor238.cartalog.data.repository.CARAPIRepository
import com.vitor238.cartalog.utils.ApiStatus
import kotlinx.coroutines.launch

class SerieViewModel : ViewModel() {
    private val tmdbRepository = CARAPIRepository()

    private var _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private var _serieInfo = MutableLiveData<Serie>()
    val serieInfo: LiveData<Serie>
        get() = _serieInfo

    fun getSerieInfo(serieId: Int) {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            val result = kotlin.runCatching { tmdbRepository.getSerieInfo(serieId) }
            result.onSuccess {
                _status.value = ApiStatus.DONE
                _serieInfo.value = it
            }.onFailure {
                _status.value = ApiStatus.ERROR
            }
        }
    }
}