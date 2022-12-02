package agency.five.codebase.android.movieapp.ui.favorites.mapper

import agency.five.codebase.android.movieapp.model.Movie
import agency.five.codebase.android.movieapp.ui.favorites.FavoritesViewState

interface FavoritesMapper {
    fun toFavoritesViewState(favoriteMovies: List<Movie>): FavoritesViewState
}
