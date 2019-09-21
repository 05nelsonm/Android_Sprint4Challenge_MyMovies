package com.lambdaschool.datapersistencesprintchallenge.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.lambdaschool.datapersistencesprintchallenge.FavoriteMovieRepoInterface
import com.lambdaschool.datapersistencesprintchallenge.model.FavoriteMovie

class FavoriteMovieRepo(val context: Context) : FavoriteMovieRepoInterface {

    override fun createEntry(entry: FavoriteMovie) {
        Thread(Runnable {
            database.favoriteMovieDao().createEntry(entry)
        }).start()
    }

    override fun readAllEntries(): LiveData<List<FavoriteMovie>> {
        return database.favoriteMovieDao().readAllEntries()
    }

    override fun deleteEntry(entry: FavoriteMovie) {
        Thread(Runnable {
            database.favoriteMovieDao().deleteEntry(entry)
        }).start()
    }

    override fun updateEntry(entry: FavoriteMovie) {
        Thread(Runnable {
            database.favoriteMovieDao().updateEntry(entry)
        }).start()
    }

    private val database by lazy {
        Room.databaseBuilder(
            context.applicationContext,
            FavoriteMovieDB::class.java, "favorite_movie_database"
        ).fallbackToDestructiveMigration().build()
    }
}