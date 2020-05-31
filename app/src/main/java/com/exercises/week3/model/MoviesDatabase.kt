package com.exercises.week3.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [MoviesModel::class], version = 1)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDAO(): MoviesDAO

    companion object {
        @Volatile
        private var instance: MoviesDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            MoviesDatabase::class.java, DATABASE_NAME
        ).allowMainThreadQueries() //don't use this line in product. it just for demo
            .build()
    }
}