package dev.ashishrawat.popularmovies.model

import java.io.Serializable

data class MovieResponse(
    val page: Long,
    val totalResults: Long,
    val totalPages: Long,
    val movies: List<Movie>
) : Serializable

data class Movie(
//    val popularity: Double,
//    val voteCount: Long,
//    val video: Boolean,
//    val posterPath: String,
//    val id: Long,
//    val adult: Boolean,
//    val backdropPath: String,
//    val originalLanguage: String,
//    val originalTitle: String,
//    val genreIDS: List<Long>,
//    val title: String,
//    val voteAverage: Double,
//    val overview: String,
//    val releaseDate: String
    val id: Int, val overview: String,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_average: Double,
    val vote_count: Int
) : Serializable

