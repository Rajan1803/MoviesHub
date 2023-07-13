package com.example.movieshub.data.model.response

import com.google.gson.annotations.SerializedName

data class CastDetail(

    @SerializedName("cast")
    val cast: List<Cast> = listOf(),

    @SerializedName("id")
    val id: Int = 0
)