package dev.ashishrawat.popularmovies.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import dev.ashishrawat.popularmovies.MainApplication
import dev.ashishrawat.popularmovies.R
import dev.ashishrawat.popularmovies.data.ApiClient
import dev.ashishrawat.popularmovies.data.MovieApiService
import dev.ashishrawat.popularmovies.model.Movie
import dev.ashishrawat.popularmovies.model.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val STARTING_PAGE_INDEX = 0


class MovieRepository {
    private var lastRequestedPage = STARTING_PAGE_INDEX

    private var movieApiService: MovieApiService? = null

    init {
        movieApiService = ApiClient.buildService(MovieApiService::class.java)
    }

    var movieList: MutableList<Movie>? = null;

    fun loadTopMovie(): Movie? {
        var movie: Movie? = null;
        movieApiService?.popularMovies(
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

    fun loadMovies(): MutableLiveData<MovieResponse?>? {
        lastRequestedPage++;
        val newsData = MutableLiveData<MovieResponse?>()
        movieApiService?.popularMovies(
            MainApplication.applicationContext().resources.getString(R.string.api_key),
            lastRequestedPage
        )
            ?.enqueue(object : Callback<MovieResponse?> {
                override fun onResponse(
                    call: Call<MovieResponse?>?,
                    response: Response<MovieResponse?>
                ) {
                    if (response.isSuccessful) {
                        movieList = ArrayList()
                        response.body()?.results?.let { movieList?.addAll(it) }
                        val movieResponse: MovieResponse? = response.body();
                        movieResponse?.results = movieList;
                        newsData.postValue(movieResponse)
                    }
                }

                override fun onFailure(call: Call<MovieResponse?>?, t: Throwable?) {
                    Log.e("Respository ", t.toString());
                }
            })
        return newsData
    }


}
