package agency.five.codebase.android.movieapp.navigation

const val MOVIE_DETAILS_ROUTE = "MovieDetails"
const val MOVIE_ID_KEY = "movieId"
const val MOVIE_DETAILS_ROUTE_WITH_PARAMS = "$MOVIE_DETAILS_ROUTE/{$MOVIE_ID_KEY}"

object MovieDetailsDestination : MovieAppDestination(MOVIE_DETAILS_ROUTE_WITH_PARAMS) {
    fun createNavigationRoute(movieId: Int): String = "$MOVIE_DETAILS_ROUTE/$movieId"
}
