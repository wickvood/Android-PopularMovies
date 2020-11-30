package dev.ashishrawat.popularmovies.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import dev.ashishrawat.popularmovies.model.Movie;

@Database(entities = {Movie.class}, version = 1)
public abstract class MovieDatabase extends RoomDatabase {

    public abstract MovieDao getNoteDao();

    private static MovieDatabase movieDb;

    public static MovieDatabase getInstance(Context context) {
        if (null == movieDb) {
            movieDb = buildDatabaseInstance(context);
        }
        return movieDb;
    }

    private static MovieDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,
                MovieDatabase.class,
                "movies")
                .allowMainThreadQueries().build();
    }

    public void cleanUp() {
        movieDb = null;
    }

}