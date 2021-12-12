package wit.assignments.randomgeneratorapp.models

import java.util.*
import kotlin.collections.ArrayList

internal fun generateBagRandomId(): Long {
    return Random().nextLong()
}

class BagMemStore : BagStore{
    var bags = ArrayList<BagModel>()

    override fun findAll(): List<BagModel> {
        return bags
    }

    override fun findOne(id: Long): BagModel? {
        return bags.find { i -> i.id == id }
    }

    override fun create(bag: BagModel) {
        bag.id = generateBagRandomId()
        bags.add(bag)//Add a list to the Item List Array List
    }

    override fun update(bag: BagModel) {
        val locbag = findOne(bag.id)
        if (locbag != null) {
            locbag.name = bag.name
            locbag.items.clear()
            for(item : Long in bag.items){
                locbag.items.add(item)
            }
        }
    }

    override fun delete(bag: BagModel) {
        val locbag = findOne(bag.id)
        if (locbag != null) {//If the list is not null, delete it
            bags.remove(locbag)
        }
    }

    override fun deleteAll(bags: BagMemStore) {
        bags.bags.clear()
    }
}