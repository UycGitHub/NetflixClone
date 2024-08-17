package com.umutyusufcinar.netflixclone.repository

import com.umutyusufcinar.netflixclone.network.ErrorResponse
import com.umutyusufcinar.netflixclone.network.NetworkResponse
import com.umutyusufcinar.netflixclone.network.model.dto.MovieDTO
import kotlinx.coroutines.CoroutineDispatcher

interface HomeDataSource {
    suspend fun getListOfMovies(dispatcher: CoroutineDispatcher, homeResultCallBack: (result: NetworkResponse<List<List<MovieDTO>>, Error>) -> Unit)
}