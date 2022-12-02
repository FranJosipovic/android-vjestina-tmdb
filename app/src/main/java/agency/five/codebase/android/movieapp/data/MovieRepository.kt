package agency.five.codebase.android.movieapp.data

import agency.five.codebase.android.movieapp.model.Movie
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.model.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun movies(movieCategory: MovieCategory): Flow<List<Movie>>
    fun movieDetails(movieId: Int): Flow<MovieDetails>
    fun favoriteMovies(): Flow<List<Movie>>
    suspend fun addMovieToFavorites(movieId: Int)
    suspend fun removeMovieFromFavorites(movieId: Int)
    suspend fun toggleFavorite(movieId: Int)
}
