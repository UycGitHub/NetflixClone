package com.umutyusufcinar.netflixclone.network

import com.umutyusufcinar.netflixclone.network.model.dto.MovieResponseDTO
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Path
import retrofit2.http.Query

interface tmdbApi  {
    @GET("/trending/{movie}/day")
    fun getTrending(@Query("language")
                    language: String,
                    @Query("page")
                    page: Int,
    ):Call<MovieResponseDTO>
}