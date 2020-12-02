package dev.ashishrawat.popularmovies.database

import androidx.paging.DataSource
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import dev.ashishrawat.popularmovies.model.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    suspend fun getAll(): List<Movie>

    @Insert
    fun insert(note: Movie?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<Movie>?)

    @Update
    fun update(repos: Movie?)

    @Delete
    fun delete(note: Movie?)


    @Query("SELECT * FROM movie")
    fun getPosts(): DataSource<Int, Movie>

    @Insert(onConflict = REPLACE)
    suspend fun saveMovies(movies: List<Movie>)
}