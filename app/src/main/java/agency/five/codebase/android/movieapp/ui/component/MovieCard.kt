package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.mock.MoviesMock.getMoviesList
import agency.five.codebase.android.movieapp.model.Movie
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import androidx.compose.foundation.clickable as clickable

@Composable
fun MovieCard(
    movieCardViewState: Movie,
    modifier: Modifier=Modifier
){
    Card(modifier = modifier
        .size(width = 150.dp, height = 210.dp)
        .clip(
            RoundedCornerShape(5.dp)
        )) {
        AsyncImage(model = movieCardViewState.imageUrl,
            contentDescription = "movie poster",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(5.dp))
                .fillMaxSize()
                .clickable { /*TODO:open movie details*/ })
        ConstraintLayout {

            val (favoriteButton) = createRefs()

            FavoriteButton(
                buttonViewState = FavoriteButtonViewState(isFavorite = movieCardViewState.isFavorite),
                modifier = Modifier.constrainAs(favoriteButton) {
                    top.linkTo(parent.top, 5.dp)
                    absoluteLeft.linkTo(parent.absoluteLeft, 5.dp)
                },
            )
        }
    }
}

@Composable
@Preview
fun MovieCardPreview(){
    val movies = getMoviesList(1)
    MovieCard(movies[0])
}