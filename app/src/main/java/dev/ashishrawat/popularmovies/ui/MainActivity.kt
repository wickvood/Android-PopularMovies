package dev.ashishrawat.popularmovies.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import dev.ashishrawat.popularmovies.R
import dev.ashishrawat.popularmovies.adaptor.MoviesAdapter
import dev.ashishrawat.popularmovies.databinding.ActivityMainBinding
import dev.ashishrawat.popularmovies.scheduler.PopularMovieWorker
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
                }
            }

        })

    }


    private fun setupScrollListener() {

        var previousTotal = 0
        var loading = true
        val visibleThreshold = 5
        var firstVisibleItem: Int
        var visibleItemCount: Int
        var totalItemCount: Int
        val layoutManager: LinearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                visibleItemCount = binding.recyclerView.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                    <= (firstVisibleItem + visibleThreshold)
                ) {
                    // End has been reached

                    Log.i("Yaeye!", "end called");

                    // Do something
                    viewModel.listScrolled();
                    loading = true;
                }
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