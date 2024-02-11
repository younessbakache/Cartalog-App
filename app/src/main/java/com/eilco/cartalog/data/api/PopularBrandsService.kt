package com.vitor238.cartalog.data.api

import com.vitor238.cartalog.BuildConfig
import com.vitor238.cartalog.data.model.PopularSeriesResult
import com.vitor238.cartalog.utils.LocaleUtils
import retrofit2.http.GET
import retrofit2.http.Query

interface PopularBrandsService {
    @GET("tv/popular/")
    suspend fun getPopularSeries(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("language") language: String = LocaleUtils.getLanguage()
    ): PopularSeriesResult
}