package agency.five.codebase.android.movieapp.data.network.model

import agency.five.codebase.android.movieapp.data.network.BASE_IMAGE_URL
import agency.five.codebase.android.movieapp.model.Actor
import agency.five.codebase.android.movieapp.model.Crewman
import agency.five.codebase.android.movieapp.model.Movie
import agency.five.codebase.android.movieapp.model.MovieDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiMovieDetails(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("overview")
    val overview: String,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("spoken_languages")
    val languages: List<ApiLanguage>,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("release_date")
    val releaseDate: String? = null,
    @SerialName("runtime")
    val runtime: Int
) {
    fun toMovieDetails(isFavorite: Boolean, crew: List<Crewman>, cast: List<Actor>) = MovieDetails(
        Movie(
            id = id,
            title = title,
            overview = overview,
            imageUrl = BASE_IMAGE_URL + posterPath,
            isFavorite = isFavorite
        ),
        voteAverage = voteAverage,
        releaseDate = releaseDate,
        language = languages[0].name,
        runtime = runtime,
        crew = crew,
        cast = cast
    )
}

@Serializable
data class ApiLanguage(
    @SerialName("english_name")
    val name: String
)
