package com.lambdaschool.datapersistencesprintchallenge

import android.app.Application
import com.lambdaschool.datapersistencesprintchallenge.database.FavoriteMovieRepo

val repo by lazy {

    App.repo!!

}

class App : Application() {

    companion object {
        var repo: FavoriteMovieRepoInterface? = null
    }

    override fun onCreate() {
        super.onCreate()

        repo = FavoriteMovieRepo(applicationContext)
    }
}