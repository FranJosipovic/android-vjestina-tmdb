package agency.five.codebase.android.movieapp.ui.main

import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.mock.MoviesMock.getActor
import agency.five.codebase.android.movieapp.mock.MoviesMock.getMoviesList
import agency.five.codebase.android.movieapp.model.MovieDetails
import agency.five.codebase.android.movieapp.ui.component.*
import agency.five.codebase.android.movieapp.ui.favorites.favoritesMapper
import agency.five.codebase.android.movieapp.ui.favorites.favoritesScreen
import agency.five.codebase.android.movieapp.ui.movieDetails.mapper.MovieDetailsMapper
import agency.five.codebase.android.movieapp.ui.movieDetails.mapper.MovieDetailsMapperImpl
import agency.five.codebase.android.movieapp.ui.movieDetails.movieDetailsScreen
import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val movieDetailsMapper: MovieDetailsMapper = MovieDetailsMapperImpl()

        val movieDetailsViewState =
            movieDetailsMapper.toMovieDetailsViewState(MoviesMock.getMovieDetails())

        //val movies = getMoviesList()
        //val favoritesViewState = favoritesMapper.toFavoritesViewState(movies)

        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme {
                //favoritesScreen(favoritesViewState = favoritesViewState)
                movieDetailsScreen(movieDetailsViewState = movieDetailsViewState)
                /*Row {
                    FavoriteButton(buttonViewState = FavoriteButtonViewState(true))
                    Column {
                        MovieCard(movieCardViewState = MovieCardViewState(movies[0].imageUrl,movies[0].isFavorite))
                        MovieCategoryLabel(movieCategoryLabelViewState = MovieCategoryLabelViewState(1,false,MovieCategoryStringParam("Crime")))
                        UserScoreProgressBar(rating = 6.8F)
                    }
                }*/
            }
        }
    }
}

@Composable
@Preview
fun MainPreview() {

    val movies = getMoviesList()

    MovieAppTheme {
        Row {
            FavoriteButton(buttonViewState = FavoriteButtonViewState(true))
            Column {
                MovieCard(movieCardViewState = MovieCardViewState(movies[0].imageUrl,
                    movies[0].isFavorite))
                /*MovieCategoryLabel(movieCategoryLabelViewState = MovieCategoryLabelViewState(1,
                    false,
                    MovieCategoryStringParam("Crime")))*/
                UserScoreProgressBar(rating = 6.8F)
            }
        }
    }
}