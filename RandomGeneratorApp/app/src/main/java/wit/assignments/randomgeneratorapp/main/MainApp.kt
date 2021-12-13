package wit.assignments.randomgeneratorapp.main

import android.app.Application
import timber.log.Timber
import timber.log.Timber.i
import wit.assignments.randomgeneratorapp.models.BagMemStore
import wit.assignments.randomgeneratorapp.models.BagModel
import wit.assignments.randomgeneratorapp.models.EntityMemStore
import java.io.Serializable

class MainApp : Application(), Serializable {

    val entities = EntityMemStore()
    val bags = BagMemStore()

    override fun onCreate() {
        bags.create(BagModel(1234,"banana"))
        bags.create(BagModel(42345,"Egg"))
        bags.create(BagModel(4432435,"Yo"))
        bags.create(BagModel(456885,"Hello"))
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Item started")
    }
}