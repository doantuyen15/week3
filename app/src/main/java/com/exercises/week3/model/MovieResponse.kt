package com.exercises.week3.model

import com.google.gson.annotations.SerializedName

data class MovieResponse (
    val page: Int,
    @SerializedName("total_results") val totalResult: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("results") val result: ArrayList<MoviesModel>
)