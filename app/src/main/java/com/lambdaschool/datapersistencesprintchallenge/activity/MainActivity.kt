package com.lambdaschool.datapersistencesprintchallenge.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lambdaschool.datapersistencesprintchallenge.R
import com.lambdaschool.datapersistencesprintchallenge.model.FavoriteMovie
import com.lambdaschool.datapersistencesprintchallenge.model.FavoriteMovieList
import com.lambdaschool.datapersistencesprintchallenge.retrofit.MyMovieAPI
import com.lambdaschool.datapersistencesprintchallenge.util.changeEntryColorAndAddToDb
import com.lambdaschool.datapersistencesprintchallenge.viewmodel.FavoriteMovieViewModel
import com.lambdaschool.sprint4challenge_mymovies.model.MovieSearchResult
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        btn_view_favorites.setOnClickListener {
            val intent = Intent(this, FavoriteMovieActivity::class.java)
            startActivity(intent)
        }

        // Load DB of favorite movies into the mlist object on create
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


        changeEntryColorAndAddToDb(view, entry, Color.CYAN, false, this, false)

        view.setPadding(15, 15, 15, 15)
        view.textSize = 22f

        view.setOnClickListener {
            changeEntryColorAndAddToDb(view, entry, Color.CYAN, true, this, false)
        }
        return view
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
