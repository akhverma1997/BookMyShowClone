package com.example.bookmyshowclone
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bookmyshowclone.data.remote.MovieRepository
import com.example.bookmyshowclone.MovieViewModel
@Suppress("UNCHECKED_CAST")
class MainViewModelFactory (private var networkHelper: NetworkHelper, private var movieRepository: MovieRepository):ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieViewModel(networkHelper,movieRepository) as T
    }
}