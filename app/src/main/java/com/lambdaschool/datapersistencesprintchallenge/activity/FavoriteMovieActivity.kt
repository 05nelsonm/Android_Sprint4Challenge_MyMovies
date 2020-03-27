package com.lambdaschool.datapersistencesprintchallenge.activity

import android.graphics.Color
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lambdaschool.datapersistencesprintchallenge.R
import com.lambdaschool.datapersistencesprintchallenge.database.FavoriteMovieRepo
import com.lambdaschool.datapersistencesprintchallenge.model.FavoriteMovie
import com.lambdaschool.datapersistencesprintchallenge.util.changeEntryColorAndAddToDb
import com.lambdaschool.datapersistencesprintchallenge.viewmodel.FavoriteMovieViewModel
import kotlinx.android.synthetic.main.activity_favorite_movie.*
import java.lang.ref.WeakReference

class FavoriteMovieActivity : AppCompatActivity() {

    lateinit var viewModel: FavoriteMovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_movie)

        viewModel = ViewModelProviders.of(this).get(FavoriteMovieViewModel::class.java)
        ReadAllAsyncTask(this).execute()

    }

    private fun updateForEntries(entries: List<FavoriteMovie>) {
        favoriteListLayout.removeAllViews()
        entries.forEach { entry ->
            favoriteListLayout.addView(createEntryView(entry))
        }
    }

    private fun createEntryView(entry: FavoriteMovie): TextView {
        val view = TextView(this@FavoriteMovieActivity)

        view.text = entry.title

        changeEntryColorAndAddToDb(view, entry, Color.YELLOW, false, this, true)

        view.setPadding(15, 15, 15, 15)
        view.textSize = 22f

        view.setOnClickListener {
            changeEntryColorAndAddToDb(view, entry, Color.YELLOW, true, this, true)
        }

        view.setOnLongClickListener {
            FavoriteMovieRepo(this).deleteEntry(entry)
            return@setOnLongClickListener true
        }

        return view
    }

    class ReadAllAsyncTask(activity: FavoriteMovieActivity) : AsyncTask<Void, Void, LiveData<List<FavoriteMovie>>?>() {

        private val activity = WeakReference(activity)

        override fun doInBackground(vararg entries: Void?): LiveData<List<FavoriteMovie>>? {
            return activity.get()?.viewModel?.entries
        }

        override fun onPostExecute(result: LiveData<List<FavoriteMovie>>?) {
            activity.get()?.let { act ->
                result?.let { entries ->
                    entries.observe(act,
                        Observer<List<FavoriteMovie>> { t ->
                            t?.let {
                                act.updateForEntries(t)
                            }
                        }
                    )
                }
            }
        }

    }
}
