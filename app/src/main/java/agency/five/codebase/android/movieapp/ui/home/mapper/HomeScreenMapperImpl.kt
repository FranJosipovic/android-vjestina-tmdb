package agency.five.codebase.android.movieapp.ui.home.mapper

import HomeMovieCategoryViewState
import HomeMovieViewState
import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.model.Movie
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.ui.component.MovieCategoryLabelTextViewState
import agency.five.codebase.android.movieapp.ui.component.MovieCategoryLabelViewState

class HomeScreenMapperImpl : HomeScreenMapper {
    override fun toHomeMovieCategoryViewState(
        movieCategories: List<MovieCategory>,
        selectedMovieCategory: MovieCategory,
        movies: List<Movie>,
    ) = HomeMovieCategoryViewState(
        movieCategories = movieCategoryLabelViewStates(movieCategories, selectedMovieCategory),
        movies = homeMovieViewStates(movies)
    )

    private fun homeMovieViewStates(movies: List<Movie>) =
        movies.map { movie ->
            HomeMovieViewState(
                id = movie.id,
                isFavorite = movie.isFavorite,
                imageUrl = movie.imageUrl
            )
        }

    private fun movieCategoryLabelViewStates(
        movieCategories: List<MovieCategory>,
        selectedMovieCategory: MovieCategory,
    ) = movieCategories.map { category ->
        MovieCategoryLabelViewState(
            itemId = category.ordinal, isSelected = category == selectedMovieCategory,
            categoryText = MovieCategoryLabelTextViewState.MovieCategoryStringResource(when (category) {
                MovieCategory.POPULAR_STREAMING -> R.string.streaming
                MovieCategory.POPULAR_ONTV -> R.string.on_tv
                MovieCategory.POPULAR_FORRENT -> R.string.for_rent
                MovieCategory.POPULAR_INTHEATRES -> R.string.in_theatres
                MovieCategory.NOWPLAYING_MOVIES -> R.string.movies
                MovieCategory.NOWPLAYING_TV -> R.string.tv
                MovieCategory.UPCOMING_TODAY -> R.string.today
                MovieCategory.UPCOMING_THISWEEK -> R.string.this_week
            })
        )
    }
}
