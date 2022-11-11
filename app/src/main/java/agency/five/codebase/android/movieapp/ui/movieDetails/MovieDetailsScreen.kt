package agency.five.codebase.android.movieapp.ui.movieDetails

import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.ui.component.*
import agency.five.codebase.android.movieapp.ui.movieDetails.mapper.MovieDetailsMapper
import agency.five.codebase.android.movieapp.ui.movieDetails.mapper.MovieDetailsMapperImpl
import agency.five.codebase.android.movieapp.ui.theme.*
import android.widget.GridView
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import androidx.constraintlayout.compose.ConstraintLayout as ConstraintLayout

private val movieDetailsMapper: MovieDetailsMapper = MovieDetailsMapperImpl()

val movieDetailsViewState = movieDetailsMapper.toMovieDetailsViewState(MoviesMock.getMovieDetails())

@Composable
fun MovieDetailsRoute(

) {
    val movieDetailsViewState by remember { mutableStateOf(movieDetailsViewState) }

    MovieDetailsScreen(
        movieDetailsViewState,
    )
}

@Composable
fun MovieDetailsScreen(
    movieDetailsViewState: MovieDetailsViewState,
    spacing: Spacing = Spacing(),
) {

    val scrollState = rememberScrollState()

    Column(Modifier.verticalScroll(scrollState)) {
        Poster(movieDetailsViewState, spacing)
        Overview(movieDetailsViewState)
        CrewGrid(movieDetailsViewState, spacing)
        ActorsRow(spacing, movieDetailsViewState)
    }
}

@Composable
private fun ActorsRow(
    spacing: Spacing,
    movieDetailsViewState: MovieDetailsViewState,
) {
    Text(
        text = "Top Billed Cast",
        Modifier.padding(Spacing().movieDetails),
        color = Blue,
        style = CustomHeader,
    )
    LazyRow(
        Modifier.padding(Spacing().movieDetails),
        horizontalArrangement = Arrangement.spacedBy(spacing.medium)
    ) {
        items(
            movieDetailsViewState.cast,
            key = { item ->
                item.id
            },
            itemContent = { item ->
                ActorCard(
                    actorCardViewState = ActorCardViewState(item.name,
                        item.character,
                        item.imageUrl),
                    modifier = Modifier
                        .background(Color.White)
                        .width(120.dp))
            }
        )
    }
}

@Composable
private fun CrewGrid(
    movieDetailsViewState: MovieDetailsViewState,
    spacing: Spacing,
) {
    LazyVerticalGrid(columns = GridCells.Fixed(3),
        Modifier.height(((movieDetailsViewState.crew.size / 3) * 100).dp),
        userScrollEnabled = false,
        contentPadding = PaddingValues(Spacing().movieDetails),
        verticalArrangement = Arrangement.spacedBy(spacing.large),
        horizontalArrangement = Arrangement.spacedBy(spacing.medium)) {
        items(
            items = movieDetailsViewState.crew,
            key = { item ->
                item.id
            },
            itemContent = { item ->

                CrewItem(crewItemViewState = CrewItemViewState(item.name, item.job))
            },
        )
    }
}

@Composable
private fun Overview(movieDetailsViewState: MovieDetailsViewState) {
    Column(Modifier.padding(Spacing().movieDetails)) {
        Text(
            text = "Overview",
            color = Blue,
            style = CustomHeader
        )
        Text(text = movieDetailsViewState.overview, style = CustomBody)
    }
}

@Composable
private fun Poster(
    movieDetailsViewState: MovieDetailsViewState,
    spacing: Spacing,
) {
    ConstraintLayout {
        val (image, column) = createRefs()
        AsyncImage(
            model = movieDetailsViewState.imageUrl,
            contentDescription = "Poster",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .constrainAs(image) {},
        )
        Column(Modifier
            .constrainAs(column) {
                bottom.linkTo(image.bottom, spacing.small)
            }
            .padding(Spacing().movieDetails)
        ) {
            Row(
                modifier = Modifier.padding(vertical = spacing.small),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                UserScoreProgressBar(rating = movieDetailsViewState.voteAverage)
                Text(
                    text = "user score",
                    modifier = Modifier.padding(horizontal = spacing.small),
                    color = Color.White,
                )
            }
            Text(
                text = movieDetailsViewState.title,
                modifier = Modifier.padding(vertical = spacing.small),
                color = Color.White,
                style = CustomHeader,
            )
            Spacer(modifier = Modifier.height(spacing.small))
            FavoriteButton(
                isFavorite = movieDetailsViewState.isFavorite,
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
fun MovieDetailsScreenPreview() {
    val movieDetailsViewState by remember { mutableStateOf(movieDetailsViewState) }
    MovieAppTheme {
        MovieDetailsScreen(
            movieDetailsViewState
        )
    }
}
