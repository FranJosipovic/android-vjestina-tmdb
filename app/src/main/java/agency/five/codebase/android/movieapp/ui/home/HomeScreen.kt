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
import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import agency.five.codebase.android.movieapp.ui.theme.Spacing
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private val homeScreenMapper: HomeScreenMapper = HomeScreenMapperImpl()

val popularCategoryViewState =
    homeScreenMapper.toHomeMovieCategoryViewState(listOf(MovieCategory.POPULAR_STREAMING,
        MovieCategory.POPULAR_ONTV,
        MovieCategory.POPULAR_FORRENT,
        MovieCategory.POPULAR_INTHEATRES), MovieCategory.POPULAR_STREAMING, getMoviesList())
val nowPlayingCategoryViewState =
    homeScreenMapper.toHomeMovieCategoryViewState(listOf(MovieCategory.NOWPLAYING_TV,
        MovieCategory.NOWPLAYING_MOVIES), MovieCategory.NOWPLAYING_TV, getMoviesList())
val upcomingCategoryViewState =
    homeScreenMapper.toHomeMovieCategoryViewState(listOf(MovieCategory.UPCOMING_TODAY,
        MovieCategory.UPCOMING_THISWEEK), MovieCategory.UPCOMING_TODAY, getMoviesList())

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
                0, 1, 2, 3 -> popularMovies =
                    homeScreenMapper.toHomeMovieCategoryViewState(listOf(
                        MovieCategory.POPULAR_STREAMING,
                        MovieCategory.POPULAR_ONTV,
                        MovieCategory.POPULAR_FORRENT,
                        MovieCategory.POPULAR_INTHEATRES),
                        MovieCategory.values()[it.itemId],
                        getMoviesList())
                4, 5 -> nowPlayingMovies =
                    homeScreenMapper.toHomeMovieCategoryViewState(listOf(
                        MovieCategory.NOWPLAYING_TV,
                        MovieCategory.NOWPLAYING_MOVIES),
                        MovieCategory.values()[it.itemId],
                        getMoviesList())
                else -> upcomingMovies =
                    homeScreenMapper.toHomeMovieCategoryViewState(listOf(
                        MovieCategory.UPCOMING_TODAY,
                        MovieCategory.UPCOMING_THISWEEK),
                        MovieCategory.values()[it.itemId],
                        getMoviesList())
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
            .padding(spacing.homeScreen),
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
