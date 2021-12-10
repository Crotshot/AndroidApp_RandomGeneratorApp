package wit.assignments.randomgeneratorapp.models
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemModel(var id: Long = 0,var name: String = "",var weight: Float = 0.0f) : Parcelable
