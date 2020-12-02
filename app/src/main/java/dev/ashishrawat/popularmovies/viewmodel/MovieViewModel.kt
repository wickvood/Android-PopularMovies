package dev.ashishrawat.popularmovies.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.ashishrawat.popularmovies.model.MovieResponse
import dev.ashishrawat.popularmovies.repository.MovieRepository
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class MovieViewModel() : ViewModel() {
    private var data: MutableLiveData<MovieResponse?>? = null
    var movieRepository: MovieRepository = MovieRepository();

    init {
        Log.e("ViewModelInit ", "Loading ViewModel Init");
        loadMovies()
    }

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }


    fun movie(): LiveData<MovieResponse?>? {
        return data;
    }

    private fun loadMovies() {
        data = movieRepository.loadMovies()
        val service: ExecutorService = Executors.newSingleThreadExecutor()
        service.submit(Runnable { // on background thread, obtain a fresh list of users

        })


    }

    fun listScrolled() {
        loadMovies()
    }
}