package com.umutyusufcinar.netflixclone.repository

import com.umutyusufcinar.netflixclone.AppConstants
import com.umutyusufcinar.netflixclone.network.ErrorResponse
import com.umutyusufcinar.netflixclone.network.NetworkResponse
import com.umutyusufcinar.netflixclone.network.TmdbApi
import com.umutyusufcinar.netflixclone.network.model.dto.MovieDTO
import com.umutyusufcinar.netflixclone.network.model.dto.MovieResponseDTO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class HomeDataSourceImpl @Inject constructor(private val tmdbApi: TmdbApi) : HomeDataSource {
    override suspend fun getListOfMovies(
        dispatcher: CoroutineDispatcher,
        homeResultCallBack: (result: NetworkResponse<List<List<MovieDTO>>, Error>) -> Unit
    ) {
        withContext(dispatcher) {
            try{
                val trendingMoviesResponse = async { tmdbApi.getTrending(AppConstants.LANGUAGE, page = 1) }
                val upcomingMoviesResponse = async { tmdbApi.getUpcomingMovies(AppConstants.LANGUAGE, 1) }
                val popularMoviesResponse = async { tmdbApi.getPopularMovies(AppConstants.LANGUAGE, 1) }
                val topRatedMoviesResponse = async { tmdbApi.getTopRatedMovies(AppConstants.LANGUAGE, 1) }

                processData(
                    homeResultCallBack,
                    trendingMoviesResponse.await(),
                    upcomingMoviesResponse.await(),
                    popularMoviesResponse.await(),
                    topRatedMoviesResponse.await()
                )
            }
            catch (e: Exception) {
                throw e
            }
        }
    }

    private fun processData(homeResultCallback: (result: NetworkResponse<List<List<MovieDTO>>, Error>) -> Unit,
                            trending: NetworkResponse<MovieResponseDTO, Error>,
                            upcoming: NetworkResponse<MovieResponseDTO, Error>,
                            popular: NetworkResponse<MovieResponseDTO, Error>,
                            topRated: NetworkResponse<MovieResponseDTO, Error>
    ) {
        val pair1 = buildResponse(trending)
        val pair2 = buildResponse(upcoming)
        val pair3 = buildResponse(popular)
        val pair4 = buildResponse(topRated)

        when {
            pair1.first == null -> {
                pair1.second?.let { homeResultCallback(it) }
                return
            }
            pair2.first == null -> {
                pair2.second?.let { homeResultCallback(it) }
                return
            }
            pair2.first == null -> {
                pair2.second?.let { homeResultCallback(it) }
                return
            }
            pair2.first == null -> {
                pair2.second?.let { homeResultCallback(it) }
                return
            }
            else -> {
                val resultList = ArrayList<List<MovieDTO>>()
                pair1.first?.let { resultList.add(it) }
                pair2.first?.let { resultList.add(it) }
                pair3.first?.let { resultList.add(it) }
                pair4.first?.let { resultList.add(it) }
                homeResultCallback(NetworkResponse.Success(resultList))
            }
        }
    }

    private fun buildResponse(response: NetworkResponse<MovieResponseDTO, Error>)
            : Pair<List<MovieDTO>?, NetworkResponse<List<List<MovieDTO>>, Error>?>
    {
        return when(response) {
            is NetworkResponse.Success -> {
                Pair(response.body.results, null)
            }
            is NetworkResponse.ApiError -> {
                Pair(null, NetworkResponse.ApiError(response.body, response.code))
            }
            is NetworkResponse.NetworkError -> {
                Pair(null, NetworkResponse.NetworkError(IOException()))
            }
            is NetworkResponse.UnknownError -> {
                Pair(null, NetworkResponse.UnknownError(Throwable()))
            }
        }
}
}