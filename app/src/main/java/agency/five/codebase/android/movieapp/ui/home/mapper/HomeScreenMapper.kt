package agency.five.codebase.android.movieapp.ui.home.mapper

import HomeMovieCategoryViewState
import agency.five.codebase.android.movieapp.model.Movie
import agency.five.codebase.android.movieapp.model.MovieCategory

interface HomeScreenMapper {

    fun toHomeMovieCategoryViewState(
        movieCategories: List<MovieCategory>,
        selectedMovieCategory: MovieCategory,
        movies: List<Movie>,
    ): HomeMovieCategoryViewState
}
