package com.example.bookmyshowclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.telecom.Call
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.bookmyshowclone.data.remote.MovieRepositoryImpl
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Callback
import retrofit2.Response
import com.example.bookmyshowclone.MovieDatabase
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MovieViewModel
    companion object {
        private const val API_KEY = "7bc0651fe0ea5973822df3bd27e779d9"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
        observeViewModel()
   //     fetchMovies()
    }

    private fun observeViewModel() {
        viewModel.movieResponse.observe(this, Observer { showMovies(it.results)
        hideProgress()
        })
        viewModel.errorResponse.observe(this, Observer { showErrorMessage(it)
        hideProgress()
        })
    }

    private fun setupViewModel(){
        showProgress()
        viewModel = ViewModelProvider(this,MainViewModelFactory(NetworkHelper(this),MovieRepositoryImpl(MovieDatabase.getInstance(this).movieDao(),RetrofitBuilder.buildService())))[MovieViewModel::class.java]
        viewModel.onCreate()
    }

    private fun showMovies(movies: List<Movie>)
    {
        recyclerView.visibility= View.VISIBLE
        progressBar.visibility= View.GONE
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator= DefaultItemAnimator()
        recyclerView.adapter= MoviesAdapter(movies)
    }

    private fun showProgress(){
        progressBar.visibility= View.VISIBLE
    }

    private fun hideProgress(){
        progressBar.visibility=View.GONE
    }

    private fun showErrorMessage(errorMessage: String?){
        errorView.visibility= View.VISIBLE
        errorView.text=errorMessage
    }
}


