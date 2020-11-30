package dev.ashishrawat.popularmovies.scheduler

import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import dev.ashishrawat.popularmovies.R
import dev.ashishrawat.popularmovies.repository.MovieRepository

class PopularMovieWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {

        // Do the work here--in this case, upload the images.
        return fetchMovie()

    }

    private fun fetchMovie(): Result {
        Result
        val movieRepository = MovieRepository()

        val movie = movieRepository.loadTopMovie();

        if (movie != null) {

            val CHANNEL_ID = "POPULAR_MOVIE";
            val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Most popular movie")
                .setContentText(movie?.title)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            with(NotificationManagerCompat.from(applicationContext)) {
                // notificationId is a unique int for each notification that you must define
                Log.e("NOtification", movie?.title.toString())

                notify(1, builder.build())

            }
            //Show notification
            Log.e("Worker", movie?.title.toString())
            return Result.success();

        } else return Result.retry();


    }

}
