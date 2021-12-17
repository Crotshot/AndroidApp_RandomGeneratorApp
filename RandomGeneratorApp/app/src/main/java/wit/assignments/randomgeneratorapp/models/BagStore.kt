package wit.assignments.randomgeneratorapp.models

interface BagStore {
    fun findAll(): List<BagModel>
    fun findOne(id: Long): BagModel?
    fun create(bag: BagModel)
    fun update(bag: BagModel)
    fun replace(bags: ArrayList<BagModel>)
    fun delete(bag: BagModel)
    fun deleteAll(bags: BagMemStore)
}