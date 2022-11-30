package agency.five.codebase.android.movieapp.ui.home

import HomeMovieCategoryViewState
import HomeMovieViewState
import agency.five.codebase.android.movieapp.mock.MoviesMock.getMoviesList
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.ui.component.MovieCard
import agency.five.codebase.android.movieapp.ui.component.MovieCardViewState
import agency.five.codebase.android.movieapp.ui.component.MovieCategoryLabel
import agency.five.codebase.android.movieapp.ui.component.MovieCategoryLabelViewState
import agency.five.codebase.android.movieapp.ui.home.mapper.HomeScreenMapper
import agency.five.codebase.android.movieapp.ui.home.mapper.HomeScreenMapperImpl
import agency.five.codebase.android.movieapp.ui.theme.CustomHeader
import agency.five.codebase.android.movieapp.ui.theme.Spacing
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private val homeScreenMapper: HomeScreenMapper = HomeScreenMapperImpl()

val popularCategoryViewState =
    homeScreenMapper.toHomeMovieCategoryViewState(
        listOf(
            MovieCategory.POPULAR_STREAMING,
            MovieCategory.POPULAR_ON_TV,
            MovieCategory.POPULAR_FOR_RENT,
            MovieCategory.POPULAR_IN_THEATRES
        ), MovieCategory.POPULAR_STREAMING, getMoviesList()
    )
val nowPlayingCategoryViewState =
    homeScreenMapper.toHomeMovieCategoryViewState(
        listOf(
            MovieCategory.NOW_PLAYING_TV,
            MovieCategory.NOW_PLAYING_MOVIES
        ), MovieCategory.NOW_PLAYING_TV, getMoviesList()
    )
val upcomingCategoryViewState =
    homeScreenMapper.toHomeMovieCategoryViewState(
        listOf(
            MovieCategory.UPCOMING_TODAY,
            MovieCategory.UPCOMING_THIS_WEEK
        ), MovieCategory.UPCOMING_TODAY, getMoviesList()
    )

@Composable
fun HomeScreenRoute(
    onNavigateToMovieDetails: (HomeMovieViewState) -> Unit,
) {
    var popularMovies by remember { mutableStateOf(popularCategoryViewState) }
    var nowPlayingMovies by remember { mutableStateOf(nowPlayingCategoryViewState) }
    var upcomingMovies by remember { mutableStateOf(upcomingCategoryViewState) }

    HomeScreen(
        popularMovies,
        nowPlayingMovies,
        upcomingMovies,
        onNavigateToMovieDetails = onNavigateToMovieDetails,
        onCategoryClick = {
            when (it.itemId) {
                MovieCategory.POPULAR_STREAMING.ordinal,
                MovieCategory.POPULAR_FOR_RENT.ordinal,
                MovieCategory.POPULAR_ON_TV.ordinal,
                MovieCategory.POPULAR_IN_THEATRES.ordinal
                -> popularMovies =
                    homeScreenMapper.toHomeMovieCategoryViewState(
                        listOf(
                            MovieCategory.POPULAR_STREAMING,
                            MovieCategory.POPULAR_ON_TV,
                            MovieCategory.POPULAR_FOR_RENT,
                            MovieCategory.POPULAR_IN_THEATRES
                        ),
                        MovieCategory.values()[it.itemId],
                        getMoviesList()
                    )
                MovieCategory.NOW_PLAYING_MOVIES.ordinal,
                MovieCategory.NOW_PLAYING_TV.ordinal
                -> nowPlayingMovies =
                    homeScreenMapper.toHomeMovieCategoryViewState(
                        listOf(
                            MovieCategory.NOW_PLAYING_TV,
                            MovieCategory.NOW_PLAYING_MOVIES
                        ),
                        MovieCategory.values()[it.itemId],
                        getMoviesList()
                    )
                else -> upcomingMovies =
                    homeScreenMapper.toHomeMovieCategoryViewState(
                        listOf(
                            MovieCategory.UPCOMING_TODAY,
                            MovieCategory.UPCOMING_THIS_WEEK
                        ),
                        MovieCategory.values()[it.itemId],
                        getMoviesList()
                    )
            }
        }
    )
}

@Composable
fun HomeScreen(
    popularMovies: HomeMovieCategoryViewState,
    nowPlayingMovies: HomeMovieCategoryViewState,
    upcomingMovies: HomeMovieCategoryViewState,
    onNavigateToMovieDetails: (HomeMovieViewState) -> Unit,
    onCategoryClick: (MovieCategoryLabelViewState) -> Unit,
    spacing: Spacing = Spacing(),
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(spacing.normal),
    ) {

        HomeHeaderCategoriesMovies(
            onCategoryClick = onCategoryClick,
            onNavigateToMovieDetails = onNavigateToMovieDetails,
            spacing = spacing,
            movieCategoryType = popularMovies,
            headerTitle = "What's popular"
        )

        HomeHeaderCategoriesMovies(
            onCategoryClick = onCategoryClick,
            onNavigateToMovieDetails = onNavigateToMovieDetails,
            spacing = spacing,
            movieCategoryType = nowPlayingMovies,
            headerTitle = "Now playing"
        )

        HomeHeaderCategoriesMovies(
            onCategoryClick = onCategoryClick,
            onNavigateToMovieDetails = onNavigateToMovieDetails,
            spacing = spacing,
            movieCategoryType = upcomingMovies,
            headerTitle = "Upcoming"
        )
    }
}

@Composable
private fun HomeHeaderCategoriesMovies(
    onCategoryClick: (MovieCategoryLabelViewState) -> Unit,
    onNavigateToMovieDetails: (HomeMovieViewState) -> Unit,
    spacing: Spacing,
    movieCategoryType: HomeMovieCategoryViewState,
    headerTitle: String,
) {
    HomeScreenHeader(spacing = spacing, text = headerTitle)

    HomeScreenCategoriesList(
        categoryTypeMovies = movieCategoryType,
        onCategoryClick = onCategoryClick,
        spacing = spacing,
    )

    HomeScreenMovieList(
        movies = movieCategoryType,
        onNavigateToMovieDetails = onNavigateToMovieDetails,
        spacing = spacing,
    )
}

@Composable
private fun HomeScreenMovieList(
    movies: HomeMovieCategoryViewState,
    onNavigateToMovieDetails: (HomeMovieViewState) -> Unit,
    spacing: Spacing,
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(spacing.medium)) {
        items(
            items = movies.movies,
            itemContent = { item ->
                MovieCard(
                    movieCardViewState = MovieCardViewState(
                        imageUrl = item.imageUrl,
                        isFavorite = item.isFavorite,
                    ),
                    modifier = Modifier
                        .height(200.dp)
                        .width(120.dp),
                    onCardClick = { onNavigateToMovieDetails(item) },
                    onFavoriteClick = {}
                )
            },
        )
    }
}

@Composable
private fun HomeScreenCategoriesList(
    categoryTypeMovies: HomeMovieCategoryViewState,
    onCategoryClick: (MovieCategoryLabelViewState) -> Unit,
    spacing: Spacing,
) {
    LazyRow(
        modifier = Modifier.padding(vertical = spacing.medium),
        horizontalArrangement = Arrangement.spacedBy(spacing.medium),
    ) {
        items(
            items = categoryTypeMovies.movieCategories,
            itemContent = { item ->
                MovieCategoryLabel(
                    movieCategoryLabelViewState = item,
                    onCategoryClick = onCategoryClick,
                )
            },
        )
    }
}

@Composable
private fun HomeScreenHeader(spacing: Spacing, text: String) {
    Text(
        text = text,
        modifier = Modifier.padding(vertical = spacing.medium),
        style = CustomHeader
    )
}

@Preview
@Composable
fun HomeScreenScreenPreview() {
    HomeScreenRoute(onNavigateToMovieDetails = { return@HomeScreenRoute })
}
