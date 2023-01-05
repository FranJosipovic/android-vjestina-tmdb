package agency.five.codebase.android.movieapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DbFavoriteMovie::class], version = 1)
abstract class MovieAppDatabase : RoomDatabase() {
    abstract fun favoriteMovieDao(): FavoriteMovieDao
}

