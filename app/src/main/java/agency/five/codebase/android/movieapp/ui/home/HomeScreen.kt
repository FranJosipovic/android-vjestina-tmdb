package agency.five.codebase.android.movieapp.ui.home

import agency.five.codebase.android.movieapp.ui.component.MovieCard
import agency.five.codebase.android.movieapp.ui.component.MovieCardViewState
import agency.five.codebase.android.movieapp.ui.component.MovieCategoryLabel
import agency.five.codebase.android.movieapp.ui.theme.CustomHeader
import agency.five.codebase.android.movieapp.ui.theme.Spacing
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreenRoute(
    viewModel: HomeViewModel,
    onNavigateToMovieDetails: (HomeMovieViewState) -> Unit,
) {

    val popularMovies: HomeMovieCategoryViewState by viewModel.popularMovies.collectAsState()
    val nowPlayingMovies: HomeMovieCategoryViewState by viewModel.nowPlayingMovies.collectAsState()
    val upcomingMovies: HomeMovieCategoryViewState by viewModel.upcomingMovies.collectAsState()

    HomeScreen(
        popularMovies,
        nowPlayingMovies,
        upcomingMovies,
        onNavigateToMovieDetails = onNavigateToMovieDetails,
        onCategoryClick = { categoryId -> viewModel.changeCategory(categoryId) },
        onFavoriteClick = { movieId -> viewModel.toggleFavorite(movieId) }
    )
}

@Composable
fun HomeScreen(
    popularMovies: HomeMovieCategoryViewState,
    nowPlayingMovies: HomeMovieCategoryViewState,
    upcomingMovies: HomeMovieCategoryViewState,
    onNavigateToMovieDetails: (HomeMovieViewState) -> Unit,
    onCategoryClick: (categoryId: Int) -> Unit,
    onFavoriteClick: (movieId: Int) -> Unit,
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
            onFavoriteClick = onFavoriteClick,
            spacing = spacing,
            movieCategoryType = popularMovies,
            headerTitle = "What's popular"
        )

        HomeHeaderCategoriesMovies(
            onCategoryClick = onCategoryClick,
            onNavigateToMovieDetails = onNavigateToMovieDetails,
            onFavoriteClick = onFavoriteClick,
            spacing = spacing,
            movieCategoryType = nowPlayingMovies,
            headerTitle = "Now playing"
        )

        HomeHeaderCategoriesMovies(
            onCategoryClick = onCategoryClick,
            onNavigateToMovieDetails = onNavigateToMovieDetails,
            onFavoriteClick = onFavoriteClick,
            spacing = spacing,
            movieCategoryType = upcomingMovies,
            headerTitle = "Upcoming"
        )
    }
}

@Composable
private fun HomeHeaderCategoriesMovies(
    onCategoryClick: (Int) -> Unit,
    onNavigateToMovieDetails: (HomeMovieViewState) -> Unit,
    onFavoriteClick: (movieId: Int) -> Unit,
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
        onFavoriteClick = onFavoriteClick,
        movies = movieCategoryType,
        onNavigateToMovieDetails = onNavigateToMovieDetails,
        spacing = spacing,
    )
}

@Composable
private fun HomeScreenMovieList(
    movies: HomeMovieCategoryViewState,
    onFavoriteClick: (movieId: Int) -> Unit,
    onNavigateToMovieDetails: (HomeMovieViewState) -> Unit,
    spacing: Spacing,
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(spacing.medium)) {
        items(
            items = movies.movies,
            itemContent = { item ->
                MovieCard(
                    movieCardViewState = MovieCardViewState(
                        id = item.id,
                        imageUrl = item.imageUrl,
                        isFavorite = item.isFavorite,
                    ),
                    modifier = Modifier
                        .height(200.dp)
                        .width(120.dp),
                    onCardClick = { onNavigateToMovieDetails(item) },
                    onFavoriteClick = onFavoriteClick
                )
            },
        )
    }
}

@Composable
private fun HomeScreenCategoriesList(
    categoryTypeMovies: HomeMovieCategoryViewState,
    onCategoryClick: (Int) -> Unit,
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
    HomeScreenRoute(viewModel = getViewModel(),
        onNavigateToMovieDetails = { return@HomeScreenRoute })
}
