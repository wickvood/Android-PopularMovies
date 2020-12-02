package dev.ashishrawat.popularmovies.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.ashishrawat.popularmovies.model.Movie

@Database(entities = [Movie::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract val noteDao: MovieDao?
    fun cleanUp() {
        movieDb = null
    }

    companion object {
        private var movieDb: MovieDatabase? = null
        fun getInstance(context: Context): MovieDatabase? {
            if (null == movieDb) {
                movieDb = buildDatabaseInstance(context)
            }
            return movieDb
        }

        private fun buildDatabaseInstance(context: Context): MovieDatabase {
            return Room.databaseBuilder(
                context,
                MovieDatabase::class.java,
                "movies.db"
            )
                .allowMainThreadQueries().build()
        }
    }
}