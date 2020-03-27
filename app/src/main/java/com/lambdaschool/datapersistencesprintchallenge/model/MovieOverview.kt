package com.lambdaschool.sprint4challenge_mymovies.model

data class MovieSearchResult(val results: List<MovieOverview>)

data class MovieOverview(
        val id: Int,
        val original_language: String,
        val original_title: String,
        val overview: String,
        val popularity: Float,
        val poster_path: String,
        val release_date: String,
        val title: String,
        val isVideo: Boolean,
        val vote_average: Float,
        val vote_count: Int
)