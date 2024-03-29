package com.vitor238.cartalog.data.model.serie

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
class ProductionCompanies(
    val name: String,
    val id: Int,
    @Json(name = "logo_path")
    val logoPath: String?
) : Parcelable
