package dev.ashishrawat.popularmovies.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class MovieResponse {
    @SerializedName("page")
    @Expose
    var page: Int? = null

    @SerializedName("total_results")
    @Expose
    var totalResults: Int? = null

    @SerializedName("total_pages")
    @Expose
    var totalPages: Int? = null

    @SerializedName("results")
    @Expose
    var results: List<Movie>? = null
}


class Movie {
    @SerializedName("popularity")
    @Expose
    var popularity: Float? = null

    @SerializedName("vote_count")
    @Expose
    var voteCount: Int? = null

    @SerializedName("video")
    @Expose
    var video: Boolean? = null

    @SerializedName("poster_path")
    @Expose
    var posterPath: String? = null

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("adult")
    @Expose
    var adult: Boolean? = null

    @SerializedName("backdrop_path")
    @Expose
    var backdropPath: String? = null

    @SerializedName("original_language")
    @Expose
    var originalLanguage: String? = null

    @SerializedName("original_title")
    @Expose
    var originalTitle: String? = null

    @SerializedName("genre_ids")
    @Expose
    var genreIds: List<Int>? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("vote_average")
    @Expose
    var voteAverage: Float? = null

    @SerializedName("overview")
    @Expose
    var overview: String? = null

    @SerializedName("release_date")
    @Expose
    var releaseDate: String? = null
}