package agency.five.codebase.android.movieapp.ui.home.mapper

import agency.five.codebase.android.movieapp.ui.home.HomeMovieCategoryViewState
import agency.five.codebase.android.movieapp.ui.home.HomeMovieViewState
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
        movieCategories = movieCategoryLabelViewStates(
            movieCategories = movieCategories,
            selectedMovieCategory = selectedMovieCategory,
        ),
        movies = homeMovieViewStates(movies = movies)
    )

    private fun homeMovieViewStates(movies: List<Movie>) =
        movies.map { movie ->
            HomeMovieViewState(
                id = movie.id,
                isFavorite = movie.isFavorite,
                imageUrl = movie.imageUrl,
            )
        }

    private fun movieCategoryLabelViewStates(
        movieCategories: List<MovieCategory>,
        selectedMovieCategory: MovieCategory,
    ) = movieCategories.map { category ->
        MovieCategoryLabelViewState(
            itemId = category.ordinal,
            isSelected = category == selectedMovieCategory,
            categoryText = MovieCategoryLabelTextViewState.MovieCategoryStringResource(
                when (category) {
                    MovieCategory.POPULAR_STREAMING -> R.string.streaming
                    MovieCategory.POPULAR_ON_TV -> R.string.on_tv
                    MovieCategory.POPULAR_FOR_RENT -> R.string.for_rent
                    MovieCategory.POPULAR_IN_THEATRES -> R.string.in_theatres
                    MovieCategory.NOW_PLAYING_MOVIES -> R.string.movies
                    MovieCategory.NOW_PLAYING_TV -> R.string.tv
                    MovieCategory.UPCOMING_TODAY -> R.string.today
                    MovieCategory.UPCOMING_THIS_WEEK -> R.string.this_week
                },
            ),
        )
    }
}
