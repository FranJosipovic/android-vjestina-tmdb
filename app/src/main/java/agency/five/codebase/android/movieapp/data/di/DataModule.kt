package agency.five.codebase.android.movieapp.data.di

import agency.five.codebase.android.movieapp.data.MovieRepository
import agency.five.codebase.android.movieapp.data.MovieRepositoryImpl
import agency.five.codebase.android.movieapp.data.database.MovieAppDatabase
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val dataModule = module {
    single<MovieRepository> {
        MovieRepositoryImpl(
            movieService = get(),
            movieDao = get<MovieAppDatabase>().favoriteMovieDao(),
            bgDispatcher = Dispatchers.IO
        )
    }
}
