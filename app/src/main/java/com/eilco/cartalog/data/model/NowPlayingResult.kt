package com.vitor238.cartalog.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NowPlayingResult(
    val dates: Dates,
    val page: Int,
    val results: List<NowPlaying>
)
