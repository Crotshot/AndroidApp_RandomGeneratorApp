package wit.assignments.randomgeneratorapp.models

interface EntityStore {
    fun findAll(): List<EntityModel>
    fun findOne(id : Long): EntityModel?
    fun create(entity: EntityModel)
    fun update(entity: EntityModel)
    fun delete(entity : EntityModel)
    fun deleteAll(items: EntityMemStore)
    //fun deleteAll(items: ItemJSONStore)
}