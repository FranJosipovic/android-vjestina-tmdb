package agency.five.codebase.android.movieapp

import android.app.Application
import android.util.Log

class MovieApp : Application() {
    override fun onCreate() {
        super.onCreate()

        Log.d("MovieApp", "App started")
    }
}
