package com.umutyusufcinar.netflixclone.network

import com.umutyusufcinar.netflixclone.network.model.dto.MovieResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi  {
    @GET("/trending/{movie}/day")
    fun getTrending(@Query("language")
                    language: String,
                    @Query("page")
                    page: Int,
    ):NetworkResponse<MovieResponseDTO, Error>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language")
        language: String,
        @Query("page")
        page: Int
    ): NetworkResponse<MovieResponseDTO, Error>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("language")
        language: String,
        @Query("page")
        page: Int
    ): NetworkResponse<MovieResponseDTO, Error>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language")
        language: String,
        @Query("page")
        page: Int
    ): NetworkResponse<MovieResponseDTO, Error>
}