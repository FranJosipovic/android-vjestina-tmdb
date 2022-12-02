package agency.five.codebase.android.movieapp.data

import agency.five.codebase.android.movieapp.data.database.DbFavoriteMovie
import agency.five.codebase.android.movieapp.data.database.FavoriteMovieDao
import agency.five.codebase.android.movieapp.data.network.MovieService
import agency.five.codebase.android.movieapp.model.Movie
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.model.MovieDetails
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

class MovieRepositoryImpl(
    private val movieService: MovieService,
    private val movieDao: FavoriteMovieDao,
    private val bgDispatcher: CoroutineDispatcher
) : MovieRepository {

    private val moviesByCategory: Map<MovieCategory, Flow<List<Movie>>> = MovieCategory.values()
        .associateWith { movieCategory ->
            flow {
                val movieResponse = when (movieCategory) {
                    MovieCategory.POPULAR_ON_TV -> movieService.fetchNowPlayingMovies()
                    MovieCategory.POPULAR_FOR_RENT -> movieService.fetchUpcomingMovies()
                    MovieCategory.POPULAR_IN_THEATRES -> movieService.fetchTopRatedMovies()
                    MovieCategory.POPULAR_STREAMING -> movieService.fetchPopularMovies()
                    MovieCategory.NOW_PLAYING_MOVIES -> movieService.fetchNowPlayingMovies()
                    MovieCategory.NOW_PLAYING_TV -> movieService.fetchTopRatedMovies()
                    MovieCategory.UPCOMING_TODAY -> movieService.fetchUpcomingMovies()
                    MovieCategory.UPCOMING_THIS_WEEK -> movieService.fetchNowPlayingMovies()
                }
                emit(movieResponse.movies)
            }.flatMapLatest { apiMovies ->
                movieDao.favorites()
                    .map { favoriteMovies ->
                        apiMovies.map { apiMovie ->
                            apiMovie.toMovie(isFavorite = favoriteMovies.any { it.id == apiMovie.id })
                        }
                    }
            }.shareIn(
                scope = CoroutineScope(bgDispatcher),
                started = SharingStarted.WhileSubscribed(1000L),
                replay = 1
            )
        }

    private val favorites = movieDao.favorites().map {
        it.map { dbFavoriteMovie ->
            Movie(
                id = dbFavoriteMovie.id,
                imageUrl = dbFavoriteMovie.posterUrl,
                title = "",
                overview = "",
                isFavorite = true
            )
        }
    }.shareIn(
        scope = CoroutineScope(bgDispatcher),
        started = SharingStarted.WhileSubscribed(1000L),
        replay = 1
    )

    override fun movies(movieCategory: MovieCategory): Flow<List<Movie>> =
        moviesByCategory[movieCategory]!!

    override fun movieDetails(movieId: Int): Flow<MovieDetails> = flow {
        emit(movieService.fetchMovieDetails(movieId) to movieService.fetchMovieCredits(movieId))
    }.flatMapLatest { (apiMovieDetails, apiMovieCredits) ->
        movieDao.favorites()
            .map { favoriteMovies ->
                apiMovieDetails.toMovieDetails(
                    isFavorite = favoriteMovies.any { it.id == apiMovieDetails.id },
                    crew = apiMovieCredits.crew.map { it.toCrewman() },
                    cast = apiMovieCredits.cast.map { it.toCast() }
                )
            }
    }.flowOn(bgDispatcher)

    override fun favoriteMovies(): Flow<List<Movie>> = favorites

    private suspend fun findMovie(movieId: Int): Movie? {
        var findMovie: Movie? = null
        MovieCategory.values().forEach { category ->
            movies(category).first().forEach { movie ->
                if (movie.id == movieId) {
                    findMovie = movie
                    return@forEach
                }
            }
        }
        return findMovie
    }

    override suspend fun addMovieToFavorites(movieId: Int) {
        withContext(bgDispatcher) {
            val movie = findMovie(movieId)
            if (movie != null) {
                movieDao.insertFavoriteMovie(
                    favoriteMovie = DbFavoriteMovie(
                        id = movie.id,
                        posterUrl = movie.imageUrl
                    )
                )
            }
        }
    }

    override suspend fun removeMovieFromFavorites(movieId: Int) {
        withContext(bgDispatcher) {
            val movie = findMovie(movieId)
            if (movie != null) {
                movieDao.deleteFavoriteMovie(
                    favoriteMovie = DbFavoriteMovie(
                        id = movie.id,
                        posterUrl = movie.imageUrl
                    )
                )
            }
        }
    }

    override suspend fun toggleFavorite(movieId: Int) {
        val favorites = movieDao.favorites().first()
        if (favorites.any { it.id == movieId }) {
            removeMovieFromFavorites(movieId)
        } else {
            addMovieToFavorites(movieId)
        }
    }
}
