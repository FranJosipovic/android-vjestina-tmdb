package agency.five.codebase.android.movieapp.model

data class MovieDetails(
    val movie: Movie,
    val voteAverage: Double,
    val releaseDate: String?,
    val language: String,
    val runtime: Int,
    val crew: List<Crewman>,
    val cast: List<Actor>,
)
