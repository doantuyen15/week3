package com.exercises.week3.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/top_rated")
    fun getTopRateMovie(@Query("page") page: Int) : Call<MovieResponse>

    @GET("movie/now_playing")
    fun getNowPlaying(@Query("page") page: Int): Call<MovieResponse>
}