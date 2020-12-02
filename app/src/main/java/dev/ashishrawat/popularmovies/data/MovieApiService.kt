package dev.ashishrawat.popularmovies.data

import dev.ashishrawat.popularmovies.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET("movie/popular")
    fun popularMovies(
        @Query("api_key") key: String,
        @Query("page") page: Int
    ): retrofit2.Call<MovieResponse>
}