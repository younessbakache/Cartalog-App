package com.vitor238.cartalog.ui.carinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vitor238.cartalog.data.model.MovieRecommendation
import com.vitor238.cartalog.data.repository.CARAPIRepository
import com.vitor238.cartalog.utils.ApiStatus
import kotlinx.coroutines.launch

class CarRecommendationViewModel : ViewModel() {
    private val tmdbRepository = CARAPIRepository()

    private var _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private var _movieRecommendations = MutableLiveData<List<MovieRecommendation>>()
    val movieRecommendation: LiveData<List<MovieRecommendation>>
        get() = _movieRecommendations

    fun getRecommendations(movieId: Int) {
        viewModelScope.launch {
            val result = kotlin.runCatching { tmdbRepository.getMovieRecommendations(movieId) }
            _status.value = ApiStatus.LOADING
            result.onSuccess {
                _status.value = ApiStatus.DONE
                _movieRecommendations.value = it
            }.onFailure {
                _status.value = ApiStatus.ERROR
            }
        }
    }
}