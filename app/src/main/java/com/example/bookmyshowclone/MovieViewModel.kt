package com.example.bookmyshowclone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bookmyshowclone.data.remote.MovieRepository

class MovieViewModel (private val networkHelper: NetworkHelper,private val movieRepository: MovieRepository):ViewModel() {
    companion object {
        private const val API_KEY = "<Your API Key>"
        private const val SOMETHING_WENT_WRONG = "Something went wrong !!"
    }
    private val _errorResponse= MutableLiveData<String>()
    val errorResponse: LiveData<String>
    get() = _errorResponse
    private val _movieResponse= MutableLiveData<MovieResponse>()
    val movieResponse: LiveData<MovieResponse>
    get() = _movieResponse
    fun onCreate(){
        if(networkHelper.isNetworkConnected()){
            movieRepository.fetchMovies(API_KEY,{movieResponse ->  _movieResponse.postValue(movieResponse)},{_errorResponse.postValue(it)})
        }else {
            movieRepository.getMoviesLocal{ movieResponse ->
                if(movieResponse!=null && movieResponse.results.isNotEmpty()){
                    _movieResponse.postValue(movieResponse)
                }
                else {
                    _errorResponse.postValue(SOMETHING_WENT_WRONG)
                }
            }
        }
    }
}