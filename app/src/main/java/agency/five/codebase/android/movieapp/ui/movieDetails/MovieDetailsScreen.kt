package agency.five.codebase.android.movieapp.ui.movieDetails

import agency.five.codebase.android.movieapp.ui.component.*
import agency.five.codebase.android.movieapp.ui.favorites.DetailsScreenViewModel
import agency.five.codebase.android.movieapp.ui.theme.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import org.koin.androidx.compose.getViewModel

@Composable
fun MovieDetailsRoute(
    viewModel: DetailsScreenViewModel,
) {
    val movieDetailsViewState: MovieDetailsViewState by viewModel.favoritesViewState.collectAsState()

    MovieDetailsScreen(
        movieDetailsViewState = movieDetailsViewState,
        onFavoriteClick = { movieId: Int ->
            viewModel.toggleFavorite(movieId)
        }

private val movieDetailsMapper: MovieDetailsMapper = MovieDetailsMapperImpl()

val movieDetailsViewState = movieDetailsMapper.toMovieDetailsViewState(MoviesMock.getMovieDetails())


@Composable
fun MovieDetailsScreen(
    movieDetailsViewState: MovieDetailsViewState,
    onFavoriteClick: (Int) -> Unit,
    spacing: Spacing = Spacing(),
) {

    val scrollState = rememberScrollState()
    Column(Modifier.verticalScroll(scrollState)) {
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
                .padding(Spacing().normal)
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
        Column(Modifier.padding(Spacing().normal)) {
            Text(
                text = "Overview",
                color = Blue,
                style = CustomHeader
            )
            Text(text = movieDetailsViewState.overview, style = CustomBody)
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            Modifier.height(((movieDetailsViewState.crew.size / 3) * 90).dp),
            contentPadding = PaddingValues(Spacing().normal),
            verticalArrangement = Arrangement.spacedBy(spacing.large),
            horizontalArrangement = Arrangement.spacedBy(spacing.medium)
        ) {
            items(
                items = movieDetailsViewState.crew,
                key = { item ->
                    item.id
                },
                itemContent = { item ->
                    CrewItem(
                        crewItemViewState = CrewItemViewState(
                            name = item.name,
                            job = item.job
                        )
                    )
                },
            )
        }
        Text(
            text = "Top Billed Cast",
            modifier = Modifier.padding(Spacing().normal),
            color = Blue,
            style = CustomHeader,
        )
        LazyRow(
            modifier = Modifier.padding(Spacing().normal),
            horizontalArrangement = Arrangement.spacedBy(spacing.medium)
        ) {
            items(
                items = movieDetailsViewState.cast,
                key = { item ->
                    item.id
                },
                itemContent = { item ->
                    ActorCard(
                        actorCardViewState = ActorCardViewState(
                            item.name,
                            item.character,
                            item.imageUrl
                        ),
                        modifier = Modifier
                            .background(Color.White)
                            .width(120.dp)
                    )
                }
            )
        }
    }
}

@Preview
@Composable
fun MovieDetailsScreenPreview() {
    MovieAppTheme {
        MovieDetailsRoute(
            viewModel = getViewModel()

        )
    }
}
