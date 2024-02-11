package com.vitor238.cartalog.data.repository

import com.vitor238.cartalog.data.api.*
import com.vitor238.cartalog.data.model.*
import com.vitor238.cartalog.data.model.movie.Movie
import com.vitor238.cartalog.data.model.serie.Serie
import com.vitor238.cartalog.utils.RetrofitInitializer


class CARAPIRepository {

    private val retrofit = RetrofitInitializer.getRetrofit()

    suspend fun getTrendingMoviesAndSeries(): List<Trend> {
        val trendsResult = retrofit.create(TrendsService::class.java)
            .getTrendingMoviesAndSeries()
        return trendsResult.results
    }

    suspend fun getPopularTVSeries(): List<PopularSerie> {
        val tvSeriesResult = retrofit.create(PopularBrandsService::class.java)
            .getPopularSeries()
        return tvSeriesResult.results
    }

    suspend fun getPopularMovies(): List<PopularMovie> {
        val popularMoviesResult = retrofit.create(PopularCarsService::class.java)
            .getPopularMovies()
        return popularMoviesResult.results
    }

    suspend fun getSerieInfo(serieId: Int): Serie {
        return retrofit.create(BrandsService::class.java)
            .getSerieInfo(tvId = serieId)
    }

    suspend fun getMovieInfo(movieId: Int): Movie {
        return retrofit.create(CarService::class.java)
            .getMovieInfo(movieId)
    }

    suspend fun getMovieRecommendations(movieId: Int): List<MovieRecommendation> {
        return retrofit.create(CarRecommendationsService::class.java)
            .getMovieRecommendations(movieId = movieId).results
    }

    suspend fun getSerieRecommendations(serieId: Int): List<SerieRecommendation> {
        return retrofit.create(BrandsRecommendationsService::class.java)
            .getSerieRecommendations(serieId = serieId).results
    }

    suspend fun getMoviesInTheaters(): List<NowPlaying> {
        return retrofit.create(LastCarsService::class.java)
            .getMoviesOnTheaters().results
    }

    suspend fun searchMoviesOrSeries(query: String): List<MediaSearch> {
        return retrofit.create(SearchService::class.java).search(query = query)
            .results
    }
}