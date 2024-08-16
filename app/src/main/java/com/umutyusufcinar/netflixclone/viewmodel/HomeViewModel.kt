package com.umutyusufcinar.netflixclone.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umutyusufcinar.netflixclone.network.NetworkResponse
import com.umutyusufcinar.netflixclone.network.TmdbApi
import com.umutyusufcinar.netflixclone.network.model.dto.MovieResponseDTO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class HomeViewModel @Inject constructor(val tmdbApi: TmdbApi): ViewModel() {
    fun getTrending(){
        viewModelScope.launch {
            val response = tmdbApi.getTrending("us", page = 1)
            when(response){
                is NetworkResponse.Success -> {
                    Log.d("HomeViewModel", "Success")

            }
            is NetworkResponse.ApiError -> {
                Log.d("HomeViewModel", "ApiError")
            }
            is NetworkResponse.NetworkError -> {
                Log.d("HomeViewModel", "NetworkError")
            }

            is NetworkResponse.UnknownError -> {
                Log.d("HomeViewModel", "UnknownError")
            }
        }
    }
    }
}