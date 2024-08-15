package com.umutyusufcinar.netflixclone.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.umutyusufcinar.netflixclone.network.TmdbApi
import com.umutyusufcinar.netflixclone.network.model.dto.MovieResponseDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class HomeViewModel @Inject constructor(val tmdbApi: TmdbApi): ViewModel() {
    fun getTrending() {
        val response: Call<MovieResponseDTO> = tmdbApi.getTrending("", page = 1)
        when(response){
            //buraya videodan devam et
        }

        tmdbApi.getTrending("us", page = 1).enqueue(object : Callback<MovieResponseDTO> {
            override fun onResponse(call: Call<MovieResponseDTO>, response: Response<MovieResponseDTO>) {
                if (response.isSuccessful) {
                    // Handle successful response
                }
            }

            override fun onFailure(call: Call<MovieResponseDTO>, t: Throwable) {
                Log.d("mytest", "onFailure: ${t.message}")
            }
        })
    }
}