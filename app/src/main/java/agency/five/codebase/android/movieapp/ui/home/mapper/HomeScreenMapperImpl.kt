package agency.five.codebase.android.movieapp.ui.home.mapper

import HomeMovieCategoryViewState
import HomeMovieViewState
import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.model.Movie
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.ui.component.MovieCategoryLabelViewState
import agency.five.codebase.android.movieapp.ui.component.MovieCategoryStringResource
import agency.five.codebase.android.movieapp.ui.home.mapper.HomeScreenMapper

class HomeScreenMapperImpl : HomeScreenMapper {
    override fun toHomeMovieCategoryViewState(
        movieCategories: List<MovieCategory>,
        selectedMovieCategory: MovieCategory,
        movies: List<Movie>,
    ): HomeMovieCategoryViewState {


        return HomeMovieCategoryViewState(
            movieCategories = movieCategories.map { category ->
                MovieCategoryLabelViewState(category.ordinal, category == selectedMovieCategory,
                MovieCategoryStringResource(when (category) {
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
            },
            movies = movies.map { movie ->
                HomeMovieViewState(movie.id,
                    movie.isFavorite,
                    movie.imageUrl)
            }
        )
    }
}