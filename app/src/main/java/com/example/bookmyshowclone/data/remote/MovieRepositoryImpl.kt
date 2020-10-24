package com.example.bookmyshowclone.data.remote

import com.example.bookmyshowclone.*
import retrofit2.Callback
import retrofit2.Response

class MovieRepositoryImpl(private val movieDao: MovieDao,private val request: MovieService) : MovieRepository {
    override fun fetchMovies(api_key: String, onSuccess:(MovieResponse)->Unit, onError:(String)->Unit) {
        val call: retrofit2.Call<MovieResponse> = request.getMovies(api_key)


        call.enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: retrofit2.Call<MovieResponse>, t: Throwable) {
                onError("Oops! Something went wrong !!")
            }

            override fun onResponse(
                call: retrofit2.Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {

                if(response.isSuccessful && response.body() != null)
                {
                    //success
                    Thread{
                        movieDao.insertMovies(response.body()!!)
                    }.start()
                    onSuccess(response.body()!!)
                }
                else
                {
                    //error
                    onError(response.message())
                }
            }

        })
    }

    override fun getMoviesLocal(onSuccess: (MovieResponse?) -> Unit) {
        Thread{
            onSuccess(movieDao.getMovies())
        }.start()
    }
}