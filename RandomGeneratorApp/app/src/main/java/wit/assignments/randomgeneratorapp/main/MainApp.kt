package wit.assignments.randomgeneratorapp.main

import android.app.Application
import timber.log.Timber
import timber.log.Timber.i
import wit.assignments.randomgeneratorapp.models.ItemModel
import wit.assignments.randomgeneratorapp.models.itemMemStore

class MainApp : Application() {

    val items = itemMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Item started")
    }
}