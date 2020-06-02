package com.exercises.week3.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


const val MOVIE_TITLE_KEY = "MOVIE_TITLE_KEY"
const val MOVIE_OVERVIEW_KEY = "MOVIE_OVERVIEW_KEY"
const val MOVIE_VOTE_KEY = "MOVIE_VOTE_KEY"
const val MOVIE_DATE_KEY = "MOVIE_DATE_KEY"
const val MOVIE_PATH_KEY = "MOVIE_PATH_KEY"

@Parcelize
@Entity(tableName = "FavoriteEntities")
data class MoviesModel(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val adult: Boolean,
//    @SerializedName("backdrop_path") val backdropPath: String,
//    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path") val poster_path: String?=null,
    @SerializedName("release_date") val releaseDate: String,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int
) : Parcelable