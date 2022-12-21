package agency.five.codebase.android.movieapp.data

import agency.five.codebase.android.movieapp.mock.FavoritesDBMock
import agency.five.codebase.android.movieapp.mock.MoviesMock.fakeMovies
import agency.five.codebase.android.movieapp.mock.MoviesMock.getMovieDetailsById
import agency.five.codebase.android.movieapp.model.Movie
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.model.MovieDetails
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext

class FakeMovieRepository(
    private val ioDispatcher: CoroutineDispatcher,
) : MovieRepository {

    private val movies: Flow<List<Movie>> = FavoritesDBMock.favoriteIds
        .mapLatest { favoriteIds ->
            fakeMovies.map { it.copy(isFavorite = favoriteIds.contains(it.id)) }
        }
        .flowOn(ioDispatcher)

    override fun popularMovies(movieCategory: MovieCategory) = movies

    override fun nowPlayingMovies(movieCategory: MovieCategory) = movies

    override fun upcomingMovies(movieCategory: MovieCategory) = movies

    override fun movieDetails(movieId: Int): Flow<MovieDetails> =
        FavoritesDBMock.favoriteIds
            .mapLatest { favoriteIds ->
                getMovieDetailsById(movieId = movieId)
                    .copy(movie = fakeMovies.first { it.id == movieId }
                        .copy(isFavorite = favoriteIds.contains(movieId))
                    )
            }
            .flowOn(ioDispatcher)

    override fun favoriteMovies(): Flow<List<Movie>> = movies.map {
        it.filter { fakeMovie -> fakeMovie.isFavorite }
    }

    override suspend fun addMovieToFavorites(movieId: Int) {
        withContext(ioDispatcher) {
            FavoritesDBMock.insert(movieId)
        }
    }

    override suspend fun removeMovieFromFavorites(movieId: Int) {
        withContext(ioDispatcher) {
            FavoritesDBMock.delete(movieId)
        }
    }

    override suspend fun toggleFavorite(movieId: Int) =
        if (FavoritesDBMock.favoriteIds.value.contains(movieId)) {
            removeMovieFromFavorites(movieId)
        } else {
            addMovieToFavorites(movieId)
        }
}
