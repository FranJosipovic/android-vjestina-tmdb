package agency.five.codebase.android.movieapp.data.di

import agency.five.codebase.android.movieapp.data.FakeMovieRepository
import agency.five.codebase.android.movieapp.data.MovieRepository
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val dataModule = module {
    single<MovieRepository> {
        FakeMovieRepository(ioDispatcher = Dispatchers.IO)
    }
}
