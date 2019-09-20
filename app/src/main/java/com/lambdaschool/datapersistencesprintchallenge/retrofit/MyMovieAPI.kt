package com.lambdaschool.datapersistencesprintchallenge.retrofit

import com.google.gson.Gson
import com.lambdaschool.sprint4challenge_mymovies.apiaccess.MovieConstants
import com.lambdaschool.sprint4challenge_mymovies.model.MovieOverview
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

interface MyMovieAPI {

    @GET("${MovieConstants.SEARCH_ENDPOINT}{name}${MovieConstants.FIXED_QUERY_PARAMS_2}")
    fun getMovieByName(@Path("name") movieName: String): Call<MovieOverview>

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