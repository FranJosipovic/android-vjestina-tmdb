package agency.five.codebase.android.movieapp.ui.home

import agency.five.codebase.android.movieapp.data.MovieRepository
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.ui.home.mapper.HomeScreenMapper
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
        MutableStateFlow(MovieCategory.POPULAR_ON_TV)
    private val nowPlayingMoviesCategorySelected: MutableStateFlow<MovieCategory> =
        MutableStateFlow(MovieCategory.NOW_PLAYING_MOVIES)
    private val upcomingMoviesCategorySelected: MutableStateFlow<MovieCategory> =
        MutableStateFlow(MovieCategory.UPCOMING_THIS_WEEK)

    @OptIn(ExperimentalCoroutinesApi::class)
    val popularMovies: StateFlow<HomeMovieCategoryViewState> =
        popularMoviesCategorySelected
            .flatMapLatest { selectedMovieCategory ->
                movieRepository
                    .movies(movieCategory = selectedMovieCategory)
                    .map { movies ->
                        homeScreenMapper.toHomeMovieCategoryViewState(
                            movieCategories = listOf(
                                MovieCategory.POPULAR_STREAMING,
                                MovieCategory.POPULAR_ON_TV,
                                MovieCategory.POPULAR_FOR_RENT,
                                MovieCategory.POPULAR_IN_THEATRES,
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
        nowPlayingMoviesCategorySelected
            .flatMapLatest { selectedMovieCategory ->
                movieRepository.movies(movieCategory = selectedMovieCategory)
                    .map { movies ->
                        homeScreenMapper.toHomeMovieCategoryViewState(
                            movieCategories = listOf(
                                MovieCategory.NOW_PLAYING_TV,
                                MovieCategory.NOW_PLAYING_MOVIES,
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
        upcomingMoviesCategorySelected
            .flatMapLatest { selectedMovieCategory ->
                movieRepository.movies(movieCategory = selectedMovieCategory)
                    .map { movies ->
                        homeScreenMapper.toHomeMovieCategoryViewState(
                            movieCategories = listOf(
                                MovieCategory.UPCOMING_TODAY,
                                MovieCategory.UPCOMING_THIS_WEEK,
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
                MovieCategory.POPULAR_ON_TV.ordinal,
                MovieCategory.POPULAR_STREAMING.ordinal,
                MovieCategory.POPULAR_IN_THEATRES.ordinal,
                MovieCategory.POPULAR_FOR_RENT.ordinal
                -> {
                    popularMoviesCategorySelected.update { MovieCategory.values()[categoryId] }
                }
                MovieCategory.NOW_PLAYING_TV.ordinal,
                MovieCategory.NOW_PLAYING_MOVIES.ordinal
                -> {
                    nowPlayingMoviesCategorySelected.update { MovieCategory.values()[categoryId] }
                }
                else -> {
                    upcomingMoviesCategorySelected.update { MovieCategory.values()[categoryId] }
                }
            }
        }
    }
}
