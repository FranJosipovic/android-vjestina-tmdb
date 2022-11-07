package agency.five.codebase.android.movieapp.ui.home

import HomeMovieCategoryViewState
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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
    onNavigateToMovieDetails: () -> Unit
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
    spacing: Spacing = Spacing(),
    onNavigateToMovieDetails: () -> Unit,
    onCategoryClick: (MovieCategoryLabelViewState) -> Unit,
) {
    LazyColumn(Modifier.padding(spacing.homeScreen)) {
        item{
            HomeScreenHeader(spacing = spacing, text = "What's popular")
        }
        item {
            HomeScreenCategoriesList(spacing = spacing, categoryTypeMovies =  popularMovies, onCategoryClick =  onCategoryClick)
        }
        item{
            HomeScreenMovieList(spacing = spacing, movies =  popularMovies, onNavigateToMovieDetails = onNavigateToMovieDetails)
        }
        item{
            HomeScreenHeader(spacing = spacing, text = "Now playing")
        }
        item {
            HomeScreenCategoriesList(spacing = spacing, categoryTypeMovies =  nowPlayingMovies, onCategoryClick =  onCategoryClick)
        }
        item{
            HomeScreenMovieList(spacing = spacing, movies =  nowPlayingMovies, onNavigateToMovieDetails = onNavigateToMovieDetails)
        }
        item{
            HomeScreenHeader(spacing = spacing, text = "Upcoming")
        }
        item {
            HomeScreenCategoriesList(spacing = spacing, categoryTypeMovies =  upcomingMovies, onCategoryClick =  onCategoryClick)
        }
        item{
            HomeScreenMovieList(spacing = spacing, movies =  upcomingMovies, onNavigateToMovieDetails = onNavigateToMovieDetails)
        }
    }
}

@Composable
private fun HomeScreenMovieList(
    spacing: Spacing,
    movies: HomeMovieCategoryViewState,
    onNavigateToMovieDetails: () -> Unit
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(spacing.medium)) {
        items(movies.movies, itemContent = { item ->
            MovieCard(movieCardViewState = MovieCardViewState(imageUrl = item.imageUrl,
                isFavorite = item.isFavorite), height = 180.dp, width = 120.dp, onNavigateToMovieDetails = onNavigateToMovieDetails)
        })
    }
}

@Composable
private fun HomeScreenCategoriesList(
    spacing: Spacing,
    categoryTypeMovies: HomeMovieCategoryViewState,
    onCategoryClick: (MovieCategoryLabelViewState) -> Unit,
) {
    LazyRow(Modifier.padding(vertical = spacing.medium),
        horizontalArrangement = Arrangement.spacedBy(spacing.medium)) {
        items(categoryTypeMovies.movieCategories, itemContent = { item ->
            MovieCategoryLabel(movieCategoryLabelViewState = item, onCategoryClick = onCategoryClick)
        })
    }
}

@Composable
private fun HomeScreenHeader(spacing: Spacing,text: String) {
    Text(text = text, Modifier.padding(vertical = spacing.medium), style = CustomHeader)
}

@Preview
@Composable
fun HomeScreenScreenPreview() {
    HomeScreenRoute(onNavigateToMovieDetails = {return@HomeScreenRoute})
}