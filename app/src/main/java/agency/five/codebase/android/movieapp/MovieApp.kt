package agency.five.codebase.android.movieapp

import agency.five.codebase.android.movieapp.data.di.dataModule
import agency.five.codebase.android.movieapp.data.di.databaseModule
import agency.five.codebase.android.movieapp.data.di.networkModule
import agency.five.codebase.android.movieapp.ui.favorites.di.favoritesModule
import agency.five.codebase.android.movieapp.ui.home.di.homeModule
import agency.five.codebase.android.movieapp.ui.movieDetails.di.movieDetailsModule
import android.app.Application
import android.util.Log
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MovieApp)
            modules(
                dataModule,
                homeModule,
                favoritesModule,
                movieDetailsModule,
                databaseModule,
                networkModule
            )
        }
        Log.d("MovieApp", "App started")
    }
}
