package agency.five.codebase.android.movieapp.ui.favorites.mapper

import FavoritesMapper
import agency.five.codebase.android.movieapp.model.Movie
import agency.five.codebase.android.movieapp.ui.favorites.FavoritesMovieViewState
import agency.five.codebase.android.movieapp.ui.favorites.FavoritesViewState

class FavoritesMapperImpl : FavoritesMapper {

    override fun toFavoritesViewState(favoriteMovies: List<Movie>) =
        FavoritesViewState(favoritesMovieViewStates(favoriteMovies = favoriteMovies))

    private fun favoritesMovieViewStates(favoriteMovies: List<Movie>) =
        favoriteMovies.map { movie ->
            FavoritesMovieViewState(
                id = movie.id,
                imageUrl = movie.imageUrl,
                isFavorite = movie.isFavorite
            )
        }
}
