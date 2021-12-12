package wit.assignments.randomgeneratorapp.models
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BagModel(
    override var id: Long = 0,
    override var name: String = "",
    val items: ArrayList<Long> = ArrayList() //An array list of items id's
) : EntityBase, Parcelable