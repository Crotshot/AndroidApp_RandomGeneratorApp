package wit.assignments.randomgeneratorapp.main

import android.app.Application
import timber.log.Timber
import timber.log.Timber.i
import wit.assignments.randomgeneratorapp.models.BagMemStore
import wit.assignments.randomgeneratorapp.models.EntityMemStore

class MainApp : Application() {

    val entities = EntityMemStore()
    val bags = BagMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Item started")
    }
}