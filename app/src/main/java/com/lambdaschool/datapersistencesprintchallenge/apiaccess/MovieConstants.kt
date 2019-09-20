package com.lambdaschool.sprint4challenge_mymovies.apiaccess

object MovieConstants {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val ENDPOINT = "search/movie?api_key="
    const val API_KEY_PARAM = "359211348348a13a2b996217f7538f45"
    const val FIXED_QUERY_PARAMS_1 = "&language=en-US&query="
    const val FIXED_QUERY_PARAMS_2 = "&page=1&include_adult=false"
    const val SEARCH_ENDPOINT = "$ENDPOINT$API_KEY_PARAM$FIXED_QUERY_PARAMS_1"
}
