package com.lambdaschool.datapersistencesprintchallenge.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.lambdaschool.datapersistencesprintchallenge.model.FavoriteMovie

@Dao
interface FavoriteMovieDAO {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun createEntry(entry: FavoriteMovie)

    @Query("SELECT * FROM favorite_movie_table")
    fun readAllEntries(): LiveData<List<FavoriteMovie>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateEntry(entry: FavoriteMovie)

    @Delete
    fun deleteEntry(entry: FavoriteMovie)
}