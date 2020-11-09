package dev.ashishrawat.popularmovies.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import dev.ashishrawat.popularmovies.R
import dev.ashishrawat.popularmovies.adaptor.MoviesAdapter
import dev.ashishrawat.popularmovies.scheduler.PopularMovieWorker
import dev.ashishrawat.popularmovies.viewmodel.MovieViewModel
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpWorkManager()

        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val viewModel: MovieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        viewModel.loadMovies()?.observe(this, Observer {
            Log.e("Response ", it.toString());
            progressBar.visibility = View.GONE
            recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(this@MainActivity, 2)
                adapter = MoviesAdapter(it?.results!!)
            }
        })

    }

    private fun setUpWorkManager() {
        //Register work manager
        val request = PeriodicWorkRequest
            .Builder(PopularMovieWorker::class.java, 1, TimeUnit.SECONDS)
            .build()
        WorkManager.getInstance(this).enqueue(request)
    }
}