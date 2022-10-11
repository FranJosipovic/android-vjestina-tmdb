package agency.five.codebase.android.movieapp.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val imageUrl: String?,
    val isFavorite: Boolean,
)
