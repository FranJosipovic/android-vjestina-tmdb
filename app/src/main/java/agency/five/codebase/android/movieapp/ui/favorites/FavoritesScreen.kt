package agency.five.codebase.android.movieapp.ui.favorites

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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val favoritesMapper: FavoritesMapper = FavoritesMapperImpl()

val favoritesViewState = favoritesMapper.toFavoritesViewState(getMoviesList())

@Composable
fun FavoritesRoute(
    onNavigateToMovieDetails: (FavoritesMovieViewState) -> Unit,
) {
    val favoritesViewState by remember { mutableStateOf(favoritesViewState) }
    FavoritesScreen(favoritesViewState, onNavigateToMovieDetails = onNavigateToMovieDetails)
}

@Composable
fun FavoritesScreen(
    favoritesViewState: FavoritesViewState,
    spacing: Spacing = Spacing(),
    onNavigateToMovieDetails: (FavoritesMovieViewState) -> Unit,
) {
    Column {
        Text(
            text = "Favorites",
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
                        imageUrl = item.imageUrl,
                        isFavorite = item.isFavorite
                    ),
                    modifier = Modifier
                        .height(200.dp)
                        .width(120.dp),
                    onCardClick = { onNavigateToMovieDetails(item) },
                    onFavoriteClick = {}
                )
            }
        }
    }
}

@Preview
@Composable

fun FavoritesScreenPreview() {
    MovieAppTheme {
        FavoritesRoute(onNavigateToMovieDetails = {})
    }
}
