package dev.ashishrawat.popularmovies.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.ashishrawat.popularmovies.R
import dev.ashishrawat.popularmovies.adaptor.MoviesAdapter
import dev.ashishrawat.popularmovies.viewmodel.MovieViewModel


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val viewModel: MovieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)

        viewModel.loadMovies()?.observe(this, Observer {
            Log.e("Response ", it.toString());
            progressBar.visibility = View.GONE
            recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = MoviesAdapter(it?.results!!)
            }
        })

    }
}