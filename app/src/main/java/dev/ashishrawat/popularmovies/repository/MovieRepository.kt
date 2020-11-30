package dev.ashishrawat.popularmovies.repository

import androidx.lifecycle.MutableLiveData
import dev.ashishrawat.popularmovies.MainApplication
import dev.ashishrawat.popularmovies.R
import dev.ashishrawat.popularmovies.data.ApiClient
import dev.ashishrawat.popularmovies.data.MovieApi
import dev.ashishrawat.popularmovies.model.Movie
import dev.ashishrawat.popularmovies.model.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MovieRepository {
    private var newsApi: MovieApi? = null

    init {
        newsApi = ApiClient.buildService(MovieApi::class.java)
    }


    fun loadTopMovie(): Movie? {
        var movie: Movie? = null;
        newsApi?.popularMovies(
            MainApplication.applicationContext().resources.getString(R.string.api_key),
            1
        )
            ?.enqueue(object : Callback<MovieResponse?> {
                override fun onResponse(
                    call: Call<MovieResponse?>?,
                    response: Response<MovieResponse?>
                ) {
                    if (response.isSuccessful) {
                        movie = response.body()?.results?.get(0);
                    }
                }

                override fun onFailure(call: Call<MovieResponse?>?, t: Throwable?) {
                    movie = null
                }
            })
        return movie
    }

    fun loadMovies(pageNumber: Int): MutableLiveData<MovieResponse?>? {
        val newsData = MutableLiveData<MovieResponse?>()
        newsApi?.popularMovies(
            MainApplication.applicationContext().resources.getString(R.string.api_key),
            pageNumber
        )
            ?.enqueue(object : Callback<MovieResponse?> {
                override fun onResponse(
                    call: Call<MovieResponse?>?,
                    response: Response<MovieResponse?>
                ) {
                    if (response.isSuccessful) {
                        newsData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<MovieResponse?>?, t: Throwable?) {
//                    newsData.value = null
                }
            })
        return newsData
    }


}
