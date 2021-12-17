package wit.assignments.randomgeneratorapp.models

import timber.log.Timber.i
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

internal fun generateItemRandomId(): Long {
    return Random().nextLong()
}

class EntityMemStore : EntityStore, Serializable {
    private val items = ArrayList<EntityModel>()

    override fun findAll(): List<EntityModel> {
        return items
    }

    override fun findOne(id: Long): EntityModel? {
        return items.find { i -> i.id == id } //Find one entity by ID
    }

    override fun create(entity: EntityModel) {
        entity.id = generateItemRandomId()
        items.add(entity)
    }

    override fun replace(entities: ArrayList<EntityModel>) {
        for(entity in entities){
            items.add(entity)
        }
    }

    override fun update(entity: EntityModel) {
        val foundEntity: EntityModel? = items.find { p -> p.id == entity.id }
        if (foundEntity != null) {
            foundEntity.name = entity.name
            foundEntity.weight = entity.weight
            logAll()
        }
    }

    override fun delete(entity: EntityModel) {
        val locItem = findOne(entity.id)
        if (locItem != null) {
            items.remove(locItem)   //Find an entity by id and delete it if it is null
        }
    }

    override fun deleteAll(items: EntityMemStore) {
        items.items.clear()
    }

    fun logAll() {
        items.forEach{ i("${it}") }
    }
}