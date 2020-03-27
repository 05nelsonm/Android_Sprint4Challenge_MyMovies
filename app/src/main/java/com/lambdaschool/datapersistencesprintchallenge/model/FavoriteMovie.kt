package com.lambdaschool.datapersistencesprintchallenge.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movie_table")
class FavoriteMovie {

    @PrimaryKey(autoGenerate = true) @NonNull
    var id: Int = 0

    var title: String? = null
    var watched: Boolean = false

    constructor(id: Int, title: String, watched: Boolean) {
        this.id = id
        this.title = title
        this.watched = watched
    }
}

object FavoriteMovieList {
    val favoriteMovieList = HashMap<Int, FavoriteMovie>()
}