package dev.ashishrawat.popularmovies.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import dev.ashishrawat.popularmovies.R
import dev.ashishrawat.popularmovies.adaptor.MoviesAdapter
import dev.ashishrawat.popularmovies.databinding.ActivityMainBinding
import dev.ashishrawat.popularmovies.scheduler.PopularMovieWorker
import dev.ashishrawat.popularmovies.utils.EndlessRecyclerViewScrollListener
import dev.ashishrawat.popularmovies.viewmodel.MovieViewModel
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MovieViewModel


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setUpWorkManager()
        setupScrollListener()
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        viewModel.movie()?.observe(this, Observer {
            if (it != null) {
                Log.e("Response ", it.toString());
                binding.progressBar.visibility = View.GONE
                binding.recyclerView.apply {
                    setHasFixedSize(true)
                    layoutManager = GridLayoutManager(this@MainActivity, 2)
                    adapter = MoviesAdapter(it?.results!!)
                    binding.recyclerView.adapter?.notifyDataSetChanged()
                }
            }

        })

    }


    private fun setupScrollListener() {
        val layoutManager = GridLayoutManager(this@MainActivity, 2)
        binding.recyclerView.layoutManager = layoutManager;
        // Retain an instance so that you can call `resetState()` for fresh searches
        val scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                Log.e("LoadMore ", "Loading Page");
                viewModel.listScrolled()

            }
        }
        // Adds the scroll listener to RecyclerView
        binding.recyclerView.addOnScrollListener(scrollListener);
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