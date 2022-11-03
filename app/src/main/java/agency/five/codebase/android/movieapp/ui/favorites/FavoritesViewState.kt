package agency.five.codebase.android.movieapp.ui.favorites

import agency.five.codebase.android.movieapp.ui.favorites.mapper.FavoritesMapperImpl


data class FavoritesMovieViewState(
    val id: Int,
    val imageUrl: String?,
    val isFavorite: Boolean,
)

data class FavoritesViewState(
    val favorites: List<FavoritesMovieViewState>,
)
