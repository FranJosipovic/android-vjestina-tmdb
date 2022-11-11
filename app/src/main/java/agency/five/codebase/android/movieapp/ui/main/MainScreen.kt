package agency.five.codebase.android.movieapp.ui.main

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.navigation.MOVIE_ID_KEY
import agency.five.codebase.android.movieapp.navigation.MovieDetailsDestination
import agency.five.codebase.android.movieapp.navigation.NavigationItem
import agency.five.codebase.android.movieapp.ui.favorites.FavoritesRoute
import agency.five.codebase.android.movieapp.ui.home.HomeScreenRoute
import agency.five.codebase.android.movieapp.ui.movieDetails.MovieDetailsRoute
import agency.five.codebase.android.movieapp.ui.theme.Blue
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val showBottomBar by remember {
        derivedStateOf {
            when (navBackStackEntry?.destination?.route) {
                MovieDetailsDestination.route -> false
                else -> true
            }
        }
    }
    val showBackIcon = !showBottomBar
    Scaffold(
        topBar = {
            TopBar(
                navigationIcon = {
                    if (showBackIcon) BackIcon(onBackClick = navController::popBackStack, it)
                }
            )
        },
        bottomBar = {
            if (showBottomBar)
                BottomNavigationBar(
                    destinations = listOf(
                        NavigationItem.HomeDestination,
                        NavigationItem.FavoritesDestination,
                    ),
                    currentDestination = navBackStackEntry?.destination,
                    onNavigateToDestination = {
                        navController.navigate(it.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
        }
    ) { padding ->
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxSize()
        ) {
            NavHost(
                navController = navController,
                startDestination = NavigationItem.HomeDestination.route,
                modifier = Modifier.padding(padding)
            ) {
                composable(NavigationItem.HomeDestination.route) {
                    HomeScreenRoute(
                        onNavigateToMovieDetails = {
                            navController.navigate(
                                MovieDetailsDestination.createNavigationRoute(it.id)
                            )
                        }
                    )
                }
                composable(NavigationItem.FavoritesDestination.route) {
                    FavoritesRoute(
                        onNavigateToMovieDetails = {
                            navController.navigate(
                                MovieDetailsDestination.createNavigationRoute(it.id)
                            )
                        }
                    )
                }
                composable(
                    route = MovieDetailsDestination.route,
                    arguments = listOf(navArgument(MOVIE_ID_KEY) { type = NavType.IntType }),
                ) {
                    MovieDetailsRoute()
                }
            }
        }
    }
}

@Composable
private fun TopBar(
    navigationIcon: @Composable ((modifier: Modifier) -> Unit)? = null,
) {
    Box(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .background(Blue),
    ) {
        navigationIcon?.invoke(
            modifier = Modifier
                .align(alignment = Alignment.CenterStart)
                .padding(start = 10.dp),
        )
        Image(
            painter = painterResource(id = R.drawable.tmdb_icon),
            contentDescription = "icon",
            modifier = Modifier
                .width(135.dp)
                .height(35.dp)
                .align(alignment = Alignment.Center),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
private fun BackIcon(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(id = R.drawable.back_icon),
        contentDescription = "back icon",
        modifier
            .width(12.dp)
            .height(20.dp)
            .clickable { onBackClick() },
    )
}

@Composable
private fun BottomNavigationBar(
    destinations: List<NavigationItem>,
    onNavigateToDestination: (NavigationItem) -> Unit,
    currentDestination: NavDestination?,
) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
    ) {
        destinations.forEach { destination ->
            BottomNavigationItem(
                selected = (destination.route == currentDestination?.route),
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        painter = if (destination.route == currentDestination?.route) {
                            painterResource(id = destination.selectedIconId)
                        } else {
                            painterResource(id = destination.unselectedIconId)
                        },
                        contentDescription = stringResource(id = destination.labelId),
                    )
                },
                label = { Text(text = stringResource(id = destination.labelId)) },
            )
        }
    }
}

@Composable
@Preview
private fun PreviewMainScreen() {
    MainScreen()
}
