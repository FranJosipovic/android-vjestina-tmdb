package agency.five.codebase.android.movieapp.ui.movieDetails

import agency.five.codebase.android.movieapp.data.MovieRepository
import agency.five.codebase.android.movieapp.ui.movieDetails.mapper.MovieDetailsMapper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailsScreenViewModel(
    private val movieRepository: MovieRepository,
    detailsMapper: MovieDetailsMapper,
    movieId: Int,
) : ViewModel() {

    val favoritesViewState: StateFlow<MovieDetailsViewState> =
        movieRepository
            .movieDetails(movieId)
            .map { it -> detailsMapper.toMovieDetailsViewState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = MovieDetailsViewState(
                    id = 1,
                    imageUrl = "",
                    voteAverage = 0F,
                    title = "",
                    overview = "",
                    isFavorite = false,
                    crew = emptyList(),
                    cast = emptyList()
                )
            )

    fun toggleFavorite(movieId: Int) {
        viewModelScope.launch {
            movieRepository.toggleFavorite(movieId)
        }
    }
}
