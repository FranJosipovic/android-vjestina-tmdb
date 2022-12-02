package agency.five.codebase.android.movieapp

import agency.five.codebase.android.movieapp.data.di.dataModule
import agency.five.codebase.android.movieapp.ui.favorites.di.favoritesModule
import agency.five.codebase.android.movieapp.ui.home.di.homeScreenModule
import agency.five.codebase.android.movieapp.ui.movieDetails.di.detailsScreenModule
import android.app.Application
import android.util.Log
import org.koin.core.context.startKoin

class MovieApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                dataModule,
                homeScreenModule,
                favoritesModule,
                detailsScreenModule
            )
        }
        Log.d("MovieApp", "App started")
    }
}
