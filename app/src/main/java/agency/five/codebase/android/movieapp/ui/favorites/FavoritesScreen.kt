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

// multiple view states if required
val favoritesViewState = favoritesMapper.toFavoritesViewState(getMoviesList())

@Composable
fun favoritesRoute(
// actions
) {
    val favoritesViewState by remember { mutableStateOf(favoritesViewState) }
// ...
    favoritesScreen(
        favoritesViewState,
// other states and actions
    )
}

@Composable
fun favoritesScreen(
    favoritesViewState: FavoritesViewState,
    spacing: Spacing = Spacing(),
) {
    Column {
        Text(text = "Favorites",
            Modifier.padding(spacing.medium),
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
            items(favoritesViewState.favorites,
                key = { item ->
                    item.id
                }, itemContent = { item ->
                    MovieCard(movieCardViewState = MovieCardViewState(item.imageUrl,
                        item.isFavorite))
                })
        }
    }
}

@Preview
@Composable

fun favoritesScreenPreview() {

    val favoritesViewState by remember { mutableStateOf(favoritesViewState) }
    MovieAppTheme {
        favoritesScreen(
            favoritesViewState
        )
    }
}