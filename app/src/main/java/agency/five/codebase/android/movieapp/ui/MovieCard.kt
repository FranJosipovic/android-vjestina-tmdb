package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.ui.theme.Spacing
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import androidx.compose.foundation.clickable as clickable

data class MovieCardViewState(
    val imageUrl: String?,
    val isFavorite: Boolean,
)

@Composable
fun MovieCard(
    movieCardViewState: MovieCardViewState,
    modifier: Modifier = Modifier,
    height: Dp = 210.dp,
    width: Dp = 150.dp,
    spacing: Spacing = Spacing(),
    onNavigateToMovieDetails: () -> Unit,
) {
    Card(modifier = modifier
        .size(width = width, height = height)
        .clip(
            shapes.small
        )) {
        AsyncImage(model = movieCardViewState.imageUrl,
            contentDescription = "movie poster",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(shapes.small)
                .fillMaxSize()
                .clickable { onNavigateToMovieDetails() })
        ConstraintLayout {

            val (favoriteButton) = createRefs()

            FavoriteButton(
                buttonViewState = FavoriteButtonViewState(isFavorite = movieCardViewState.isFavorite),
                modifier = Modifier.constrainAs(favoriteButton) {
                    top.linkTo(parent.top, spacing.medium)
                    absoluteLeft.linkTo(parent.absoluteLeft, spacing.small)
                },
            )
        }
    }
}

@Composable
@Preview
fun MovieCardPreview() {
    val movie = MovieCardViewState(
        imageUrl = "https://image.tmdb.org/t/p/w500/rjkmN1dniUHVYAtwuV3Tji7FsDO.jpg",
        isFavorite = false
    )
    MovieCard(movie, onNavigateToMovieDetails = return)
}