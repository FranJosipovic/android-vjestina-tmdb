package agency.five.codebase.android.movieapp.ui.favorites

import FavoritesMapper
import agency.five.codebase.android.movieapp.data.MovieRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val movieRepository: MovieRepository,
    favoritesMapper: FavoritesMapper,
) : ViewModel() {

    val favoritesViewState: StateFlow<FavoritesViewState> =
        movieRepository
            .favoriteMovies()
            .map { movies ->
                favoritesMapper.toFavoritesViewState(movies)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = FavoritesViewState(emptyList())
            )

    fun removeMovieFromFavorites(movieId: Int) {
        viewModelScope.launch {
            movieRepository.removeMovieFromFavorites(movieId)
        }
    }
}
