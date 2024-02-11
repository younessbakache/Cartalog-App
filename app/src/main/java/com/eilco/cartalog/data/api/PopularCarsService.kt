package com.vitor238.cartalog.data.api

import com.vitor238.cartalog.BuildConfig
import com.vitor238.cartalog.data.model.PopularMoviesResult
import com.vitor238.cartalog.utils.LocaleUtils
import retrofit2.http.GET
import retrofit2.http.Query

interface PopularCarsService {

    @GET("movie/popular/")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("language") language: String = LocaleUtils.getLanguage(),
        @Query("region") region: String = LocaleUtils.getCountry()
    ): PopularMoviesResult
}