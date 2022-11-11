package agency.five.codebase.android.movieapp.ui.movieDetails.mapper

import agency.five.codebase.android.movieapp.model.MovieDetails
import agency.five.codebase.android.movieapp.ui.movieDetails.MovieDetailsViewState

interface MovieDetailsMapper {
    fun toMovieDetailsViewState(movieDetails: MovieDetails): MovieDetailsViewState
}
