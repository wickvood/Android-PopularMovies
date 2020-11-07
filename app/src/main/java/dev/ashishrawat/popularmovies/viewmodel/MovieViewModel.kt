package dev.ashishrawat.popularmovies.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.ashishrawat.popularmovies.model.MovieResponse
import dev.ashishrawat.popularmovies.repository.MovieRepository


class MovieViewModel() : ViewModel() {


    var movieRepository: MovieRepository = MovieRepository();
    fun loadMovies(): MutableLiveData<MovieResponse?>? {
        return movieRepository.loadMovies();

    }

}