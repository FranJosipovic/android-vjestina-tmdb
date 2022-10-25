package agency.five.codebase.android.movieapp.ui.main

import agency.five.codebase.android.movieapp.mock.MoviesMock.getActor
import agency.five.codebase.android.movieapp.mock.MoviesMock.getMoviesList
import agency.five.codebase.android.movieapp.ui.component.*
import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val movies = getMoviesList(1)

        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme {
                /*Row {
                    ActorCard(getActor())
                    FavoriteButton(buttonViewState = FavoriteButtonViewState(true))
                    Column {
                        MovieCard(movieCardViewState = movies[0])
                        MovieCategoryLabel(movieCategoryLabelViewState = MovieCategoryLabelViewState(1,false,MovieCategoryStringParam("Crime")))
                        UserScoreProgressBar(rating = 6.8F)
                    }
                }*/
            }
        }
    }
}
