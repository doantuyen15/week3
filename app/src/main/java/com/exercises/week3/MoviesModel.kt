package com.exercises.week3
const val MOVIE_TITLE_KEY = "MOVIE_TITLE_KEY"
const val MOVIE_OVERVIEW_KEY = "MOVIE_OVERVIEW_KEY"
const val MOVIE_VOTE_KEY = "MOVIE_VOTE_KEY"
const val MOVIE_DATE_KEY = "MOVIE_DATE_KEY"
const val MOVIE_PATH_KEY = "MOVIE_PATH_KEY"
var viewMode : String = "List"

data class MoviesModel(
    val poster_path: String,
    val title: String,
    val overview: String,
    val vote_average: Float?=1F,
    val release_date: String?="Coming soon!")