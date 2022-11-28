package agency.five.codebase.android.movieapp.mock

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object FavoritesDBMock {
    private val favoriteIdsState = MutableStateFlow(setOf<Int>())

    val favoriteIds: StateFlow<Set<Int>> = favoriteIdsState.asStateFlow()

    fun insert(movieId: Int) = favoriteIdsState.update { it + movieId }

    fun delete(movieId: Int) = favoriteIdsState.update { it - movieId }
}
