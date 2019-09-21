package com.lambdaschool.datapersistencesprintchallenge.util

import android.content.Context
import android.view.View
import com.lambdaschool.datapersistencesprintchallenge.database.FavoriteMovieRepo
import com.lambdaschool.datapersistencesprintchallenge.model.FavoriteMovie
import com.lambdaschool.datapersistencesprintchallenge.model.FavoriteMovieList

fun changeEntryColorAndAddToDb(
    view: View,
    entry: FavoriteMovie,
    colorInt: Int,
    wasClicked: Boolean,
    context: Context,
    callingFromFavoriteMovieActivity: Boolean
) {

    // if the entry is not on the list
    if (!FavoriteMovieList.favoriteMovieList.containsKey(entry.id)) {

        // if the entry was clicked, to put on the list
        if (wasClicked) {

            view.setBackgroundColor(colorInt)
            FavoriteMovieRepo(context).createEntry(entry)

        // if the entry was not being clicked and was not on the list
        } else {
            view.setBackgroundColor(0)
        }

    // if the entry is on the list (shows up in FavoriteMovieActivity, too)
    } else {

        // if the entry was clicked to take off of the list or change to not watched
        if (wasClicked) {

            if (callingFromFavoriteMovieActivity) {

                if (entry.watched) {

                    entry.watched = false
                    view.setBackgroundColor(0)

                } else {

                    entry.watched = true
                    view.setBackgroundColor(colorInt)

                }

                FavoriteMovieRepo(context).updateEntry(entry)

            } else {
                FavoriteMovieRepo(context).deleteEntry(entry)
            }

        // if the entry was not being clicked, but was on the list
        } else {

            if (callingFromFavoriteMovieActivity) {
                if (entry.watched) {
                    view.setBackgroundColor(colorInt)
                } else {
                    view.setBackgroundColor(0)
                }
            } else {
                view.setBackgroundColor(colorInt)
            }
        }
    }
}