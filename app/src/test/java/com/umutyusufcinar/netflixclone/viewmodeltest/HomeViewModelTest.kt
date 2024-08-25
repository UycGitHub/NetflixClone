package com.umutyusufcinar.netflixclone.viewmodeltest

import com.umutyusufcinar.netflixclone.repository.HomeDataSourceImpl
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.umutyusufcinar.netflixclone.AppConstants
import com.umutyusufcinar.netflixclone.network.NetworkResponse
import com.umutyusufcinar.netflixclone.network.TmdbApi
import com.umutyusufcinar.netflixclone.network.model.dto.MovieDTO
import com.umutyusufcinar.netflixclone.network.model.dto.MovieResponseDTO
import com.umutyusufcinar.netflixclone.repository.HomeDataSource
import com.umutyusufcinar.netflixclone.viewmodel.HomeViewModel
import kotlinx.coroutines.CoroutineDispatcher
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
class HomeViewModelTest{
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val dispatcher = TestCoroutineDispatcher()
    private var homeDataSourceMock : HomeDataSourceMock? = null
    private var moviesListMock: List<MovieDTO> = listOf(MovieDTO(1, "", "", ""))
    private var listsOfMoviesMock: List<List<MovieDTO>> = listOf(moviesListMock, moviesListMock, moviesListMock, moviesListMock)

    init {
        listsOfMoviesMock = listOf()
    }

    @Before
    fun init() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `when LISTS OF MOVIES request returns SUCCESSFULLY expect live data lists filled`() = dispatcher.runBlockingTest {
        //Arrange
        homeDataSourceMock = HomeDataSourceMock(NetworkResponse.Success(listsOfMoviesMock))
        val viewModel = HomeViewModel(homeDataSourceMock!!, dispatcher) //değişecek
        // Act
        viewModel.getListOfMovies()
        // Assert
        assertEquals(listsOfMoviesMock, viewModel?.listsOfMovies?.value) //ileride değişecek
        assertEquals(false, viewModel?.errorScreenVisibility?.value)
        assertEquals(null, viewModel?.errorMessage?.value)

    }

    @Test
    fun `when LISTS OF MOVIES request returns API ERROR expect error live data filled`() = dispatcher.runBlockingTest {
        // Arrange
        homeDataSourceMock =
            HomeDataSourceMock(
                getListsOfMoviesResponse = NetworkResponse.ApiError(Error(), 400)
            )
        viewModel = HomeViewModel(context, tmdbApi, homeDataSourceMock!!, dispatcher)
        // Act
        viewModel?.getListOfMovies()
        // Assert
        assertEquals(null, viewModel?.listsOfMovies?.value)
        assertEquals(true, viewModel?.errorScreenVisibility?.value)
        assertEquals(AppConstants.API_ERROR_MESSAGE, viewModel?.errorMessage?.value)
    }

    @Test
    fun `when LISTS OF MOVIES request returns NETWORK ERROR expect error live data filled`() = dispatcher.runBlockingTest {
        // Arrange
        homeDataSourceMock =
            HomeDataSourceMock(
                getListsOfMoviesResponse = NetworkResponse.NetworkError(IOException())
            )
        viewModel = HomeViewModel(context, tmdbApi, homeDataSourceMock!!, dispatcher)
        // Act
        viewModel?.getListOfMovies()
        // Assert
        assertEquals(null, viewModel?.listsOfMovies?.value)
        assertEquals(true, viewModel?.errorScreenVisibility?.value)
        assertEquals(AppConstants.NETWORK_ERROR_MESSAGE, viewModel?.errorMessage?.value)
    }

    @Test
    fun `when LISTS OF MOVIES request returns UNKNOWN ERROR expect error live data filled`() = dispatcher.runBlockingTest {
        // Arrange
        homeDataSourceMock =
            HomeDataSourceMock(
                getListsOfMoviesResponse = NetworkResponse.UnknownError(Throwable())
            )
        viewModel = HomeViewModel(context, tmdbApi, homeDataSourceMock!!, dispatcher)
        // Act
        viewModel?.getListOfMovies()
        // Assert
        assertEquals(null, viewModel?.listsOfMovies?.value)
        assertEquals(true, viewModel?.errorScreenVisibility?.value)
        assertEquals(AppConstants.UNKNOWN_ERROR_MESSAGE, viewModel?.errorMessage?.value)
    }

}

class HomeDataSourceMock(private val result: NetworkResponse<List<List<MovieDTO>>, Error>) : HomeDataSource{
    override suspend fun getListOfMovies(
        dispatcher: CoroutineDispatcher,
        homeResultCallBack: (result: NetworkResponse<List<List<MovieDTO>>, Error>) -> Unit
    ) {
        homeResultCallBack(result)
    }

}
