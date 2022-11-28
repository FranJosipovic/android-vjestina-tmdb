package agency.five.codebase.android.movieapp.ui.main

import agency.five.codebase.android.movieapp.data.di.dataModule
import agency.five.codebase.android.movieapp.ui.favorites.di.favoritesModule
import agency.five.codebase.android.movieapp.ui.movieDetails.di.detailsScreenModule
import agency.five.codebase.android.movieapp.ui.home.di.homeScreenModule
import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin {
            modules(
                dataModule,
                homeScreenModule,
                favoritesModule,
                detailsScreenModule
            )
        }
        setContent {
            MovieAppTheme {
                MainScreen()
            }
        }
    }
}

@Composable
@Preview
fun MainPreview() {
    MovieAppTheme {
        MainScreen()
    }
}
