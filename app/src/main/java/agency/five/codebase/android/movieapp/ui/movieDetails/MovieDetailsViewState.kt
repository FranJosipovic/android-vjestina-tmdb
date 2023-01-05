package agency.five.codebase.android.movieapp.ui.movieDetails


data class CrewmanViewState(
    val id: Int,
    val name: String,
    val job: String,
)

data class ActorViewState(
    val id: Int,
    val name: String,
    val character: String,
    val imageUrl: String?,
)

data class MovieDetailsViewState(
    val id: Int,
    val imageUrl: String,
    val voteAverage: Double,
    val title: String,
    val overview: String,
    val isFavorite: Boolean,
    val crew: List<CrewmanViewState>,
    val cast: List<ActorViewState>,
)
