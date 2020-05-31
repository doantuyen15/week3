package com.exercises.week3.model

import androidx.room.*

@Dao
interface MoviesDAO {
    @Query("SELECT * FROM FavoriteEntities")
    fun getAll(): List<MoviesModel>

    @Insert
    fun insertAll(vararg account: MoviesModel) : List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: MoviesModel): Long

    @Delete
    fun delete(account: MoviesModel)

    @Update
    fun update(account: MoviesModel)

    @Query("DELETE FROM FavoriteEntities")
    fun deleteAll()
}