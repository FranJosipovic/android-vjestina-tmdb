package agency.five.codebase.android.movieapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {
    @Insert
    fun insertFavoriteMovie(favoriteMovie: DbFavoriteMovie)

    @Delete
    fun deleteFavoriteMovie(favoriteMovie: DbFavoriteMovie)

    @Query("SELECT * FROM favorites")
    fun favorites(): Flow<List<DbFavoriteMovie>>
}
