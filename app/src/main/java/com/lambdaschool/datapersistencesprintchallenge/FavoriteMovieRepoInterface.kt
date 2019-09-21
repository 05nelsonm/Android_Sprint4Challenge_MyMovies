package com.lambdaschool.datapersistencesprintchallenge

import androidx.lifecycle.LiveData
import com.lambdaschool.datapersistencesprintchallenge.model.FavoriteMovie

interface FavoriteMovieRepoInterface {
    fun createEntry(entry: FavoriteMovie)
    fun readAllEntries(): LiveData<List<FavoriteMovie>>
    fun updateEntry(entry: FavoriteMovie)
    fun deleteEntry(entry: FavoriteMovie)
}