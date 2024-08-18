package com.umutyusufcinar.netflixclone.repository

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.umutyusufcinar.netflixclone.AppConstants
import com.umutyusufcinar.netflixclone.network.NetworkResponse
import com.umutyusufcinar.netflixclone.network.TmdbApi
import com.umutyusufcinar.netflixclone.network.model.dto.MovieDTO
import com.umutyusufcinar.netflixclone.network.model.dto.MovieResponseDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class HomeDataSourceImplTest{
    @get:Rule
    val rule = InstantTaskExecutorRule()
    private val dispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var tmdbApi: TmdbApi

    @Mock
    private lateinit var context: Context
    //@Mock
    //private lateinit var myListDao: MyListDao

    private val movieResponseDTO = MovieResponseDTO(listOf(MovieDTO(0, "", "", "")))

    private lateinit var homeDataSourceImpl: HomeDataSourceImpl

    @Before
    fun init() {
        homeDataSourceImpl = HomeDataSourceImpl(tmdbApi)
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `when ALL 4 REQUESTS returns SUCCESSFULLY expect success network response`() = dispatcher.runBlockingTest {
        // Arrange
        `when`(tmdbApi.getTrending(AppConstants.LANGUAGE, 1)).thenReturn(
            NetworkResponse.Success(movieResponseDTO)
        )
        `when`(tmdbApi.getUpcomingMovies(AppConstants.LANGUAGE, 1)).thenReturn(
            NetworkResponse.Success(movieResponseDTO)
        )
        `when`(tmdbApi.getPopularMovies(AppConstants.LANGUAGE, 1)).thenReturn(
            NetworkResponse.Success(movieResponseDTO)
        )
        `when`(tmdbApi.getTopRatedMovies(AppConstants.LANGUAGE, 1)).thenReturn(
            NetworkResponse.Success(movieResponseDTO)
        )
        var response: NetworkResponse<List<List<MovieDTO>>, Error>? = null
        // Act
        homeDataSourceImpl.getListOfMovies(dispatcher) {
            response = it
        }
        // Assert
        assertTrue(response is NetworkResponse.Success)
        assertEquals(movieResponseDTO.results, (response as NetworkResponse.Success).body[0])
    }

    @Test
    fun `when 1 OF THE REQUESTS returns API ERROR expect api error response`() = dispatcher.runBlockingTest {
        // Arrange
        `when`(tmdbApi.getTrending(AppConstants.LANGUAGE, 1)).thenReturn(
            NetworkResponse.ApiError(Error(), 400)
        )
        `when`(tmdbApi.getUpcomingMovies(AppConstants.LANGUAGE, 1)).thenReturn(
            NetworkResponse.Success(movieResponseDTO)
        )
        `when`(tmdbApi.getPopularMovies(AppConstants.LANGUAGE, 1)).thenReturn(
            NetworkResponse.Success(movieResponseDTO)
        )
        `when`(tmdbApi.getTopRatedMovies(AppConstants.LANGUAGE, 1)).thenReturn(
            NetworkResponse.Success(movieResponseDTO)
        )
        var response: NetworkResponse<List<List<MovieDTO>>, Error>? = null
        // Act
        homeDataSourceImpl.getListOfMovies(dispatcher) {
            response = it
        }
        // Assert
        assertTrue(response is NetworkResponse.ApiError)
    }

    @Test
    fun `when 1 OF THE REQUESTS returns NETWORK ERROR expect api error response`() = dispatcher.runBlockingTest {
        // Arrange
        `when`(tmdbApi.getTrending(AppConstants.LANGUAGE, 1)).thenReturn(
            NetworkResponse.NetworkError(IOException())
        )
        `when`(tmdbApi.getUpcomingMovies(AppConstants.LANGUAGE, 1)).thenReturn(
            NetworkResponse.Success(movieResponseDTO)
        )
        `when`(tmdbApi.getPopularMovies(AppConstants.LANGUAGE, 1)).thenReturn(
            NetworkResponse.Success(movieResponseDTO)
        )
        `when`(tmdbApi.getTopRatedMovies(AppConstants.LANGUAGE, 1)).thenReturn(
            NetworkResponse.Success(movieResponseDTO)
        )
        var response: NetworkResponse<List<List<MovieDTO>>, Error>? = null
        // Act
        homeDataSourceImpl.getListOfMovies(dispatcher) {
            response = it
        }
        // Assert
        assertTrue(response is NetworkResponse.NetworkError)
    }

    @Test
    fun `when 1 OF THE REQUESTS returns UNKNOWN ERROR expect api error response`() = dispatcher.runBlockingTest {
        // Arrange
        `when`(tmdbApi.getTrending(AppConstants.LANGUAGE, 1)).thenReturn(
            NetworkResponse.UnknownError(Throwable())
        )
        `when`(tmdbApi.getUpcomingMovies(AppConstants.LANGUAGE, 1)).thenReturn(
            NetworkResponse.Success(movieResponseDTO)
        )
        `when`(tmdbApi.getPopularMovies(AppConstants.LANGUAGE, 1)).thenReturn(
            NetworkResponse.Success(movieResponseDTO)
        )
        `when`(tmdbApi.getTopRatedMovies(AppConstants.LANGUAGE, 1)).thenReturn(
            NetworkResponse.Success(movieResponseDTO)
        )
        var response: NetworkResponse<List<List<MovieDTO>>, Error>? = null
        // Act
        homeDataSourceImpl.getListOfMovies(dispatcher) {
            response = it
        }
        // Assert
        assertTrue(response is NetworkResponse.UnknownError)
    }
}
