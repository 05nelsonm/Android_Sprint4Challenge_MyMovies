package com.lambdaschool.datapersistencesprintchallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lambdaschool.datapersistencesprintchallenge.model.FavoriteMovie
import com.lambdaschool.datapersistencesprintchallenge.repo

class FavoriteMovieViewModel : ViewModel() {

    val entries: LiveData<List<FavoriteMovie>> by lazy {
        readAllEntries()
    }

    fun createEntry(entry: FavoriteMovie) {
        repo.createEntry(entry)
    }

    fun readAllEntries() : LiveData<List<FavoriteMovie>> {
        return repo.readAllEntries()
    }

    fun updateEntry(entry: FavoriteMovie) {
        repo.updateEntry(entry)
    }

    fun deleteEntry(entry: FavoriteMovie) {
        repo.deleteEntry(entry)
    }
}