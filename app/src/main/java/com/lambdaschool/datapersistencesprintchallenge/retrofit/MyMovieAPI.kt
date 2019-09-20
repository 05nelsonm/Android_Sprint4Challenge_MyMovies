package com.lambdaschool.datapersistencesprintchallenge.retrofit

import com.google.gson.Gson
import com.lambdaschool.sprint4challenge_mymovies.apiaccess.MovieConstants
import com.lambdaschool.sprint4challenge_mymovies.model.MovieSearchResult
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface MyMovieAPI {

    //https://api.themoviedb.org/3/search/movie?api_key=359211348348a13a2b996217f7538f45&language=en-US&query=Batman&page=1&include_adult=false

    @GET("${MovieConstants.SEARCH_MOVIES_ENDPOINT}")
    fun getMovieByName(@Query("query") movieTitle: String, @Query("api_key") apiKey: String = MovieConstants.API_KEY_PARAM) : Call<MovieSearchResult>

    class Factory {

        companion object {
            val gson = Gson()
            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
                level = HttpLoggingInterceptor.Level.BODY
            }


            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(logger)
                .retryOnConnectionFailure(false)
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build()

            fun create(): MyMovieAPI {

                return Retrofit.Builder()
                    .baseUrl(MovieConstants.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                    .create(MyMovieAPI::class.java)
            }
        }
    }

}