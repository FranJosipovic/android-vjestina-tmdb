package agency.five.codebase.android.movieapp.ui.home

import HomeMovieCategoryViewState
import agency.five.codebase.android.movieapp.mock.MoviesMock.getMoviesList
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.ui.component.MovieCategoryLabel
import agency.five.codebase.android.movieapp.ui.component.MovieCategoryLabelViewState
import agency.five.codebase.android.movieapp.ui.home.mapper.HomeScreenMapper
import agency.five.codebase.android.movieapp.ui.home.mapper.HomeScreenMapperImpl
import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

// imports
private val homeScreenMapper: HomeScreenMapper = HomeScreenMapperImpl()
// multiple view states if required

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
fun homeScreenRoute(
// actions
) {
    var popularMovies by remember { mutableStateOf(popularCategoryViewState) }
    var nowPlayingMovies by remember { mutableStateOf(nowPlayingCategoryViewState) }
    var upcomingMovies by remember { mutableStateOf(upcomingCategoryViewState) }


// ...
    homeScreen(
        popularMovies,
        nowPlayingMovies,
        upcomingMovies,
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
// other states and actions
    )
}


@Composable
fun homeScreen(
    popularMovies: HomeMovieCategoryViewState,
    nowPlayingMovies: HomeMovieCategoryViewState,
    upcomingMovies: HomeMovieCategoryViewState,
    onCategoryClick: (MovieCategoryLabelViewState) -> Unit
// other states and actions
) {
    LazyColumn {
        item {
            LazyRow() {
                items(popularMovies.movieCategories, itemContent = { item ->
                    MovieCategoryLabel(movieCategoryLabelViewState = item) {

                    }
                })
            }
        }
    }
}

@Preview
@Composable
fun homeScreenScreenPreview() {
    val popularCategoryViewState by remember { mutableStateOf(popularCategoryViewState) }
    val nowPlayingCategoryViewState by remember { mutableStateOf(nowPlayingCategoryViewState) }
    val upcomingCategoryViewState by remember { mutableStateOf(upcomingCategoryViewState) }
    MovieAppTheme {
        homeScreen(
            popularCategoryViewState,
            nowPlayingCategoryViewState,
            upcomingCategoryViewState
        )
    }
}