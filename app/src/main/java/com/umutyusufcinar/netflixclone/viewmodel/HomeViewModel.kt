package com.umutyusufcinar.netflixclone.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umutyusufcinar.netflixclone.AppConstants
import com.umutyusufcinar.netflixclone.di.IoDispatcher
import com.umutyusufcinar.netflixclone.network.NetworkResponse
import com.umutyusufcinar.netflixclone.network.TmdbApi
import com.umutyusufcinar.netflixclone.network.model.dto.MovieDTO
import com.umutyusufcinar.netflixclone.network.model.dto.MovieResponseDTO
import com.umutyusufcinar.netflixclone.repository.HomeDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
//responses are tried before
class HomeViewModel @Inject constructor(val context: Context, private val tmdbApi: TmdbApi, private val homeDataSource: HomeDataSource, @IoDispatcher private val ioDispatcher: CoroutineDispatcher) : ViewModel() {

    private val _listsOfMovies: MutableLiveData<List<List<MovieDTO>>?> = MutableLiveData()
    val listsOfMovies: LiveData<List<List<MovieDTO>>?> = _listsOfMovies



    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = _isLoading

    private var _errorScreenVisibility = MutableLiveData<Boolean>(false)
    var errorScreenVisibility: LiveData<Boolean> = _errorScreenVisibility

    private var _errorMessage = MutableLiveData<String?>()
    var errorMessage: MutableLiveData<String?> = _errorMessage

    init {
        getListOfMovies()
    }

    fun getListOfMovies() {
        showErrorScreen(false)
        try {
            viewModelScope.launch(ioDispatcher) {

                homeDataSource.getListOfMovies(ioDispatcher) { result ->
                    when (result) {
                        is NetworkResponse.Success -> {
                            _listsOfMovies.postValue(result.body)
                        }
                        is NetworkResponse.ApiError -> {
                            showErrorScreen(show = true, message = AppConstants.API_ERROR_MESSAGE)
                        }
                        is NetworkResponse.NetworkError -> {
                            showErrorScreen(
                                show = true,
                                message = AppConstants.NETWORK_ERROR_MESSAGE
                            )
                        }
                        is NetworkResponse.UnknownError -> {
                            showErrorScreen(
                                show = true,
                                message = AppConstants.UNKNOWN_ERROR_MESSAGE
                            )
                        }
                    }
                }
            }
        } catch (exception: Exception){
            throw exception
        }
    }



    private fun showErrorScreen(show: Boolean, message: String? = null) {
        _errorMessage.postValue(message)
        _errorScreenVisibility.postValue(show)
        _isLoading.postValue(!show)
    }



}