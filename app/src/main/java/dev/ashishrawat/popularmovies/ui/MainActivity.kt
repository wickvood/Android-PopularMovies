package dev.ashishrawat.popularmovies.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.work.*
import dev.ashishrawat.popularmovies.R
import dev.ashishrawat.popularmovies.adaptor.MoviesAdapter
import dev.ashishrawat.popularmovies.databinding.ActivityMainBinding
import dev.ashishrawat.popularmovies.scheduler.PopularMovieWorker
import dev.ashishrawat.popularmovies.viewmodel.MovieViewModel
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setUpWorkManager()

        val viewModel: MovieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        viewModel.loadMovies()?.observe(this, Observer {
            Log.e("Response ", it.toString());
            binding.progressBar.visibility = View.GONE
            binding.recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(this@MainActivity, 2)
                adapter = MoviesAdapter(it?.results!!)
            }
        })

    }

    private fun setUpWorkManager() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        //Register work manager
        val request = PeriodicWorkRequest
            .Builder(PopularMovieWorker::class.java, 1, TimeUnit.SECONDS)
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork("loadtopmovies", ExistingPeriodicWorkPolicy.KEEP, request)
    }
}