package agency.five.codebase.android.movieapp.ui.favorites

import agency.five.codebase.android.movieapp.ui.component.MovieCard
import agency.five.codebase.android.movieapp.ui.component.MovieCardViewState
import FavoritesMapper
import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.mock.MoviesMock.getMoviesList
import agency.five.codebase.android.movieapp.ui.component.MovieCard
import agency.five.codebase.android.movieapp.ui.component.MovieCardViewState
import agency.five.codebase.android.movieapp.ui.favorites.mapper.FavoritesMapperImpl
import agency.five.codebase.android.movieapp.ui.theme.Blue
import agency.five.codebase.android.movieapp.ui.theme.CustomHeader
import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import agency.five.codebase.android.movieapp.ui.theme.Spacing
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.getViewModel

@Composable
fun FavoritesRoute(
    onNavigateToMovieDetails: (FavoritesMovieViewState) -> Unit,
    viewModel: FavoritesViewModel,
) {
    val favoritesViewState: FavoritesViewState by viewModel.favoritesViewState.collectAsState()
    FavoritesScreen(
        favoritesViewState = favoritesViewState,
        onFavoriteClick = { movieId: Int -> viewModel.removeMovieFromFavorites(movieId) },
        onNavigateToMovieDetails = onNavigateToMovieDetails,
    )
)  {
    Column {
        Text(
            text = if (favoritesViewState.favorites.isEmpty()) {
                "Your favorites list is empty"
            } else {
                "Favorites"
            },
            modifier = Modifier.padding(spacing.medium),
            color = Blue,
            style = CustomHeader
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(spacing.large),
            horizontalArrangement = Arrangement.spacedBy(spacing.medium)
        ) {
            items(
                items = favoritesViewState.favorites,
                key = { item ->
                    item.id
                }
            ) { item ->
                MovieCard(
                    movieCardViewState = MovieCardViewState(
                        id = item.id,
                        imageUrl = item.imageUrl,
                        isFavorite = item.isFavorite
                    ),
                    modifier = Modifier
                        .height(200.dp)
                        .width(120.dp),
                    onCardClick = { onNavigateToMovieDetails(item) },
                    onFavoriteClick = onFavoriteClick
                )
            }
        }
    }
}

@Preview
@Composable

fun FavoritesScreenPreview() {
    MovieAppTheme {
        FavoritesRoute(onNavigateToMovieDetails = {}, viewModel = getViewModel())
    }
}
