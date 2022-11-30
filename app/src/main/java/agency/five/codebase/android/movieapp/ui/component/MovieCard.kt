package agency.five.codebase.android.movieapp.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

data class MovieCardViewState(
    val imageUrl: String?,
    val isFavorite: Boolean,
)

@Composable
fun MovieCard(
    movieCardViewState: MovieCardViewState,
    onCardClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .clip(
                shapes.small
            )
    ) {
        Box() {
            AsyncImage(model = movieCardViewState.imageUrl,
                contentDescription = "movie poster",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(shapes.small)
                    .fillMaxSize()
                    .clickable { onCardClick() })
            FavoriteButton(
                isFavorite = movieCardViewState.isFavorite,
                onClick = onFavoriteClick,
                modifier = Modifier.padding(start = 10.dp, top = 10.dp)
            )
        }
    }
}

@Composable
@Preview
fun MovieCardPreview() {
    var thisMovie = MovieCardViewState(
        imageUrl = "https://image.tmdb.org/t/p/w500/rjkmN1dniUHVYAtwuV3Tji7FsDO.jpg",
        isFavorite = false
    )

    var movie by remember {
        mutableStateOf(thisMovie)
    }

    MovieCard(
        movieCardViewState = movie,
        onCardClick = {},
        onFavoriteClick = {
            movie = MovieCardViewState(
                imageUrl = movie.imageUrl,
                isFavorite = !movie.isFavorite
            )
        },
        modifier = Modifier
            .height(200.dp)
            .width(120.dp)
    )
}
