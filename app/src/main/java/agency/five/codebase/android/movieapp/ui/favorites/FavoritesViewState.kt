package agency.five.codebase.android.movieapp.ui.favorites

data class FavoritesMovieViewState(
    val id: Int,
    val imageUrl: String?,
    val isFavorite: Boolean,
)

data class FavoritesViewState(
    val favorites: List<FavoritesMovieViewState>,
)
