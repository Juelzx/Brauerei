package julian.scholler.brauerei

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class BreweryApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}