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

    val entities = EntityMemStore()
    val bags = BagMemStore()

    override fun onCreate() {
        bags.create(BagModel(1234,"banana"))
        bags.create(BagModel(42345,"Egg"))
        bags.create(BagModel(4432435,"Yo"))
        bags.create(BagModel(456885,"Hello"))
        entities.create(EntityModel(3123, "Entity", 213.9093f))
        entities.create(EntityModel(3244343, "Fragment", 213.9093f))
        entities.create(EntityModel(334443556743, "has", 213.9093f))
        entities.create(EntityModel(3123, "been", 213.9093f))
        entities.create(EntityModel(7567523, "implemented", 213.9093f))
        entities.create(EntityModel(678867, "completely", 213.9093f))
        entities.create(EntityModel(789789, "Hurray!!!", 213.9093f))
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Item started")
    }
}