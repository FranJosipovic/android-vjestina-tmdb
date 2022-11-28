package agency.five.codebase.android.movieapp.ui.home

import HomeMovieCategoryViewState
import agency.five.codebase.android.movieapp.data.MovieRepository
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.navigation.MovieDetailsDestination
import agency.five.codebase.android.movieapp.ui.home.mapper.HomeScreenMapper
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val movieRepository: MovieRepository,
    private val homeScreenMapper: HomeScreenMapper,
) : ViewModel() {

    private val popularMoviesCategorySelected: MutableStateFlow<MovieCategory> =
        MutableStateFlow(MovieCategory.POPULAR_ONTV)
    private val nowPlayingMoviesCategorySelected: MutableStateFlow<MovieCategory> =
        MutableStateFlow(MovieCategory.NOWPLAYING_MOVIES)
    private val upcomingMoviesCategorySelected: MutableStateFlow<MovieCategory> =
        MutableStateFlow(MovieCategory.UPCOMING_THISWEEK)

    @OptIn(ExperimentalCoroutinesApi::class)
    val popularMovies: StateFlow<HomeMovieCategoryViewState> =
        popularMoviesCategorySelected
            .flatMapLatest { selectedMovieCategory ->
                movieRepository
                    .popularMovies(movieCategory = selectedMovieCategory)
                    .map { movies ->
                        homeScreenMapper.toHomeMovieCategoryViewState(
                            movieCategories = listOf(
                                MovieCategory.POPULAR_STREAMING,
                                MovieCategory.POPULAR_ONTV,
                                MovieCategory.POPULAR_FORRENT,
                                MovieCategory.POPULAR_INTHEATRES,
                            ),
                            selectedMovieCategory = selectedMovieCategory,
                            movies = movies,
                        )
                    }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = HomeMovieCategoryViewState(emptyList(), emptyList())
            )


    @OptIn(ExperimentalCoroutinesApi::class)
    val nowPlayingMovies: StateFlow<HomeMovieCategoryViewState> =
        nowPlayingMoviesCategorySelected.flatMapLatest { selectedMovieCategory ->
            movieRepository.popularMovies(movieCategory = selectedMovieCategory)
                .map { movies ->
                    homeScreenMapper.toHomeMovieCategoryViewState(
                        movieCategories = listOf(
                            MovieCategory.NOWPLAYING_TV,
                            MovieCategory.NOWPLAYING_MOVIES,
                        ),
                        selectedMovieCategory = selectedMovieCategory,
                        movies = movies,
                    )
                }
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = HomeMovieCategoryViewState(emptyList(), emptyList())
            )

    @OptIn(ExperimentalCoroutinesApi::class)
    val upcomingMovies: StateFlow<HomeMovieCategoryViewState> =
        upcomingMoviesCategorySelected.flatMapLatest { selectedMovieCategory ->
            movieRepository.popularMovies(movieCategory = selectedMovieCategory)
                .map { movies ->
                    homeScreenMapper.toHomeMovieCategoryViewState(
                        movieCategories = listOf(
                            MovieCategory.UPCOMING_TODAY,
                            MovieCategory.UPCOMING_THISWEEK,
                        ),
                        selectedMovieCategory = selectedMovieCategory,
                        movies = movies,
                    )
                }
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = HomeMovieCategoryViewState(emptyList(), emptyList())
            )

    fun toggleFavorite(movieId: Int) {
        viewModelScope.launch {
            movieRepository.toggleFavorite(movieId = movieId)
        }
    }

    fun changeCategory(categoryId: Int) {
        viewModelScope.launch {
            when (categoryId) {
                0, 1, 2, 3 -> {
                    popularMoviesCategorySelected.update { MovieCategory.values()[categoryId] }
                }
                4, 5 -> {
                    nowPlayingMoviesCategorySelected.update { MovieCategory.values()[categoryId] }
                }
                else -> {
                    upcomingMoviesCategorySelected.update { MovieCategory.values()[categoryId] }
                }
            }
        }
    }
}