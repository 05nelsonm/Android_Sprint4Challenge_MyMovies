package com.lambdaschool.datapersistencesprintchallenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.lambdaschool.datapersistencesprintchallenge.retrofit.MyMovieAPI
import com.lambdaschool.sprint4challenge_mymovies.model.MovieOverview
import com.lambdaschool.sprint4challenge_mymovies.model.MovieSearchResult
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), Callback<MovieSearchResult> {

    lateinit var myMovieService: MyMovieAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myMovieService = MyMovieAPI.Factory.create()

        btn_search.setOnClickListener {
            listLayout.removeAllViews()
            progressBar.visibility = View.VISIBLE
            getMovieByName(et_search.text.toString())
        }
    }

    private fun getMovieByName(movieName: String) {
        myMovieService.getMovieByName(movieName).enqueue(this)
    }

    private fun createEntryView(entry: MovieOverview): TextView {
        val view = TextView(this@MainActivity)

        view.text = entry.title

        /*if (!entry.title exists in the DB) {
            view.setBackgroundColor(Color.CYAN)
        } else {
            view.setBackgroundColor(Color.TRANSPARENT)
        }*/

        view.setPadding(15, 15, 15, 15)
        view.textSize = 22f

        view.setOnClickListener {
            /*if (!entry.title exists in the DB) {
                view.setBackgroundColor(Color.CYAN)
                // remove MovieOverview from DB
            } else {
                view.setBackgroundColor(Color.TRANSPARENT)
                // store MovieOverview in DB
            }*/
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
                listLayout.addView(createEntryView(it))
            }
        }
    }
}
