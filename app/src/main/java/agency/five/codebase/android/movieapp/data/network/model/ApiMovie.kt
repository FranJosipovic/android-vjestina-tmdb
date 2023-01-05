package agency.five.codebase.android.movieapp.data.network.model

import agency.five.codebase.android.movieapp.data.network.BASE_IMAGE_URL
import agency.five.codebase.android.movieapp.model.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val movies: List<ApiMovie>,
)

@Serializable
data class ApiMovie(
    @SerialName("adult")
    val adult: Boolean,
    @SerialName("backdrop_path")
    val bdt: String,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    @SerialName("id")
    val id: Int,
    @SerialName("original_language")
    val lan: String,
    @SerialName("original_title")
    val original_title: String,
    @SerialName("overview")
    val overview: String,
    @SerialName("popularity")
    val popularity: Double,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("release_date")
    val releaseDate: String? = null,
    @SerialName("title")
    val title: String,
    @SerialName("video")
    val video: Boolean,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val vote_count: Int
) {
    fun toMovie(isFavorite: Boolean) = Movie(
        id = id,
        title = title,
        overview = overview,
        imageUrl = "$BASE_IMAGE_URL/$posterPath",
        isFavorite = isFavorite
    )
}
