package wit.assignments.randomgeneratorapp.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class itemMemStore : ItemStore {
    val items = ArrayList<ItemModel>()

    override fun findAll(): List<ItemModel> {
        return items
    }

    override fun create(item: ItemModel) {
        item.id = getId()
        items.add(item)
        logAll()
    }

    override fun update(item: ItemModel) {
        var foundItem: ItemModel? = items.find { p -> p.id == item.id }
        if (foundItem != null) {
            foundItem.name = item.name
            foundItem.weight = item.weight
            logAll()
        }
    }

    fun logAll() {
        items.forEach{ i("${it}") }
    }
}