package wit.assignments.randomgeneratorapp.models

interface ItemStore {
    fun findAll(): List<ItemModel>
    fun create(item: ItemModel)
    fun update(item: ItemModel)
}