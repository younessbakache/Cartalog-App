package com.vitor238.cartalog.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Dates(
    val maximum: String,
    val minimum: String
)
