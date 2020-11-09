package dev.ashishrawat.popularmovies.scheduler

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import dev.ashishrawat.popularmovies.repository.MovieRepository

class PopularMovieWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {

        // Do the work here--in this case, upload the images.
        fetchMovie()

        // Indicate whether the work finished successfully with the Result
        return Result.success()
    }

    private fun fetchMovie() {

        val movieRepository: MovieRepository = MovieRepository()

        val movieResponse = movieRepository.loadMovies()?.value;

        val movie = movieResponse?.results?.get(0);

        //Show notification
        Log.e("Worker", movie?.originalTitle.toString())

    }
}
