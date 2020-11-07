package dev.ashishrawat.popularmovies.data

import dev.ashishrawat.popularmovies.model.MovieResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object ApiClient {
    private const val API_BASE_URL = "https://api.themoviedb.org/3/"
    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}

interface MovieApi {
    @GET("movie/popular")
    fun popularMovies(@Query("api_key") key: String): Call<MovieResponse>
}