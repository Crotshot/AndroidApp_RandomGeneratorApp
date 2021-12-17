package wit.assignments.randomgeneratorapp.main

import android.app.Application
import timber.log.Timber
import timber.log.Timber.i
import wit.assignments.randomgeneratorapp.models.BagMemStore
import wit.assignments.randomgeneratorapp.models.BagModel
import wit.assignments.randomgeneratorapp.models.EntityMemStore
import wit.assignments.randomgeneratorapp.models.EntityModel
import java.io.Serializable

class MainApp : Application(), Serializable {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("App Starting")
    }
}