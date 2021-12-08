package wit.assignments.randomgeneratorapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import wit.assignments.randomgeneratorapp.databinding.ActivityRandomBinding
import wit.assignments.randomgeneratorapp.models.ItemModel
//import timber.log.Timber.i
import wit.assignments.randomgeneratorapp.R

class RandomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRandomBinding
    var item = ItemModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val snackbar = Snackbar.make(
            findViewById(android.R.id.content),
            "Welcome To Random Generator App",
            Snackbar.LENGTH_LONG
        )
        snackbar.show()
        binding = ActivityRandomBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.addItem.setOnClickListener {
            item.name = binding.itemName.text.toString()
            if (item.name.isNotEmpty()) {
                Snackbar.make(findViewById(android.R.id.content),"Added item $item.name", Snackbar.LENGTH_LONG).show()
            }
            else {
                Snackbar.make(findViewById(android.R.id.content),"Please Enter an item name", Snackbar.LENGTH_LONG).show()
            }
//            Timber.plant(Timber.DebugTree())
//            val greetingText = getString(R.string.greeting_text)
//            val duration = Toast.LENGTH_SHORT
//            val toast = Toast.makeText(applicationContext, greetingText, duration)
//            toast.show()
        }

    }
}