package dev.ashishrawat.popularmovies.database

import androidx.room.*
import dev.ashishrawat.popularmovies.model.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    suspend fun getAll(): List<Movie>

    @Insert
    fun insert(note: Movie?)

    @Insert
    suspend fun insertAll(users: List<Movie>?)

    @Update
    fun update(repos: Movie?)


    @Delete
    fun delete(note: Movie?)

    @Delete
    fun delete(vararg note: Movie?) // Note... is varargs, here note is an array
}