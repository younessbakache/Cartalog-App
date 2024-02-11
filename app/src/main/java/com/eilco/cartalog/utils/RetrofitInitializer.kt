package com.vitor238.cartalog.utils

import com.vitor238.cartalog.utils.BaseUrls.BASE_TMDB_URL
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInitializer {

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_TMDB_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
}