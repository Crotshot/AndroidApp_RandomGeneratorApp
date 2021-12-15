package wit.assignments.randomgeneratorapp.models
import android.os.Parcelable
import androidx.core.app.NotificationCompat
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class EntityModel(
    override var id: Long = 0,
    override var name: String = "",
    var weight: Float  = 0.01F, //Value used to as an items chance of being selected from a list
) : EntityBase, Parcelable, Serializable
