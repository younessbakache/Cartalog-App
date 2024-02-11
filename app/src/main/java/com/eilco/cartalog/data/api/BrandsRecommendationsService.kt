package com.vitor238.cartalog.data.api

import com.vitor238.cartalog.BuildConfig
import com.vitor238.cartalog.data.model.SerieRecommendationsResult
import com.vitor238.cartalog.utils.LocaleUtils
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BrandsRecommendationsService {
    @GET("tv/{tv_id}/recommendations")
    suspend fun getSerieRecommendations(
        @Path("tv_id") serieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("language") language: String = LocaleUtils.getLanguage()
    ): SerieRecommendationsResult
}