package wit.assignments.randomgeneratorapp.models
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EntityModel(
    override var id: Long = 0,
    override var name: String = "",
    var weight: Float  = 0.01F, //Value used to as an items chance of being selected from a list
) : EntityBase, Parcelable
