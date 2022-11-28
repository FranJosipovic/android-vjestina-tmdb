package agency.five.codebase.android.movieapp.data

import agency.five.codebase.android.movieapp.mock.FavoritesDBMock
import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.mock.MoviesMock.getMoviesList
import agency.five.codebase.android.movieapp.model.Movie
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.model.MovieDetails
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest

class FakeMovieRepository(
    private val ioDispatcher: CoroutineDispatcher,
) : MovieRepository {

    private fun getMovieDetails(movieId: Int): MovieDetails = MovieDetails(
        movie = fakeMovies.first { it.id == movieId },
        voteAverage = MoviesMock.getMovieDetails().voteAverage,
        releaseDate = MoviesMock.getMovieDetails().releaseDate,
        language = MoviesMock.getMovieDetails().language,
        runtime = MoviesMock.getMovieDetails().runtime,
        crew = MoviesMock.getMovieDetails().crew,
        cast = MoviesMock.getMovieDetails().cast
    )

    private val fakeMovies = getMoviesList().toMutableList()

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
                getMovieDetails(movieId = movieId)
                    .copy(movie = fakeMovies.first { it.id == movieId }
                        .copy(isFavorite = favoriteIds.contains(movieId))
                    )
            }
            .flowOn(ioDispatcher)

    override fun favoriteMovies(): Flow<List<Movie>> = movies.map {
        it.filter { fakeMovie -> fakeMovie.isFavorite }
    }

    override suspend fun addMovieToFavorites(movieId: Int) {
        FavoritesDBMock.insert(movieId)
    }

    override suspend fun removeMovieFromFavorites(movieId: Int) {
        FavoritesDBMock.delete(movieId)
    }

    override suspend fun toggleFavorite(movieId: Int) =
        if (FavoritesDBMock.favoriteIds.value.contains(movieId)) {
            removeMovieFromFavorites(movieId)
        } else {
            addMovieToFavorites(movieId)
        }
}
