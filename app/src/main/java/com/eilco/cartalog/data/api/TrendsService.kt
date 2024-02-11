package com.vitor238.cartalog.data.api

import com.vitor238.cartalog.BuildConfig
import com.vitor238.cartalog.data.model.TrendsResult
import com.vitor238.cartalog.utils.LocaleUtils
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TrendsService {

    @GET("")
    suspend fun getTrendingMoviesAndSeries(

        @Query("limit") apiKey: String = "10",
        @Query("refine") language: String = "make:ford"
    ): TrendsResult
}