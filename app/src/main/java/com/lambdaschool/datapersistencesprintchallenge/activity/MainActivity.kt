package com.lambdaschool.datapersistencesprintchallenge.activity

import android.content.Context
import android.graphics.Color
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lambdaschool.datapersistencesprintchallenge.FavoriteMovieRepoInterface
import com.lambdaschool.datapersistencesprintchallenge.R
import com.lambdaschool.datapersistencesprintchallenge.database.FavoriteMovieDAO
import com.lambdaschool.datapersistencesprintchallenge.database.FavoriteMovieRepo
import com.lambdaschool.datapersistencesprintchallenge.model.FavoriteMovie
import com.lambdaschool.datapersistencesprintchallenge.model.FavoriteMovieList
import com.lambdaschool.datapersistencesprintchallenge.retrofit.MyMovieAPI
import com.lambdaschool.datapersistencesprintchallenge.viewmodel.FavoriteMovieViewModel
import com.lambdaschool.sprint4challenge_mymovies.model.MovieSearchResult
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity(), Callback<MovieSearchResult> {

    lateinit var myMovieService: MyMovieAPI
    lateinit var viewModel: FavoriteMovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myMovieService = MyMovieAPI.Factory.create()

        btn_search.setOnClickListener {
            if (et_search.text.isNotEmpty()) {
                listLayout.removeAllViews()
                progressBar.visibility = View.VISIBLE
                getMovieByName(et_search.text.toString())
            }
        }

        // Load DB of favorite movies into the mlist object on create
        //FavoriteMovieRepo(this).readAllEntries()
        viewModel = ViewModelProviders.of(this).get(FavoriteMovieViewModel::class.java)
        viewModel.readAllEntries().observe(this, Observer{it.forEach { t ->
            FavoriteMovieList.favoriteMovieList.put(t.id, t)
        }})
    }

    private fun getMovieByName(movieName: String) {
        myMovieService.getMovieByName(movieName).enqueue(this)
    }

    private fun createEntryView(entry: FavoriteMovie): TextView {
        val view = TextView(this@MainActivity)

        view.text = entry.title

        changeEntryColor(view, entry, false)

        view.setPadding(15, 15, 15, 15)
        view.textSize = 22f

        view.setOnClickListener {
            changeEntryColor(view, entry, true)
        }
        return view
    }

    private fun changeEntryColor(view: View, entry: FavoriteMovie, wasClicked: Boolean) {

        // if the entry is not on the list
        if (!FavoriteMovieList.favoriteMovieList.containsKey(entry.id)) {

            // if the entry was clicked
            if (wasClicked) {

                // set background to Cyan, add it to the list && the DB
                view.setBackgroundColor(Color.CYAN)
                //FavoriteMovieList.favoriteMovieList.put(entry.id, entry)
                FavoriteMovieRepo(this).createEntry(entry)

                // if the entry was not being clicked and was not on the list
            } else {

                //set background to nothing
                view.setBackgroundColor(0)
            }

            // if the entry is on the list
        } else {

            // if the entry was clicked
            if (wasClicked) {

                // set background to nothing, remove it from the list && DB
                view.setBackgroundColor(0)
                //FavoriteMovieList.favoriteMovieList.remove(entry.id)
                FavoriteMovieRepo(this).deleteEntry(entry)

                // if the entry was not being clicked, but was on the list
            } else {

                // set background to cyan
                view.setBackgroundColor(Color.CYAN)
            }
        }
    }

    override fun onFailure(call: Call<MovieSearchResult>, t: Throwable) {
        progressBar.visibility = View.GONE
        t.printStackTrace()
        val response = "faliure; ${t.printStackTrace()}"
        Toast.makeText(this@MainActivity, response, Toast.LENGTH_SHORT).show()
    }

    override fun onResponse(call: Call<MovieSearchResult>, response: Response<MovieSearchResult>) {
        progressBar.visibility = View.GONE
        if (response.isSuccessful) {
            val movieSearchResults = response.body() as MovieSearchResult
            movieSearchResults.results.forEach {
                listLayout.addView(createEntryView(FavoriteMovie(it.id, it.title, false)))
            }
        }
    }
}
