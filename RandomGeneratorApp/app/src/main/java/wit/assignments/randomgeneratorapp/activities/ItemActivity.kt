package wit.assignments.randomgeneratorapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber.i
import wit.assignments.randomgeneratorapp.databinding.ActivityItemBinding
import wit.assignments.randomgeneratorapp.models.ItemModel
import wit.assignments.randomgeneratorapp.main.MainApp

class ItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemBinding
    var item = ItemModel()
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as MainApp

        if (intent.hasExtra("placemark_edit")) {
            item = intent.extras?.getParcelable("placemark_edit")!!
            binding.itemName.setText(item.name)
            binding.itemWeight.setText(item.weight.toString())
        }

        binding = ActivityItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addItem.setOnClickListener {
            item.name = binding.itemName.text.toString()
            item.weight = binding.itemWeight.text.toString().toFloat()
            if (item.name.isNotEmpty() && item.weight > 0) {
                app.items.create(item.copy())
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar.make(findViewById(android.R.id.content),"Please Enter an item name and a weight greater than 0", Snackbar.LENGTH_LONG).show()
            }
        }
        binding.cancel.setOnClickListener {
            Snackbar.make(findViewById(android.R.id.content),"Item Addition Cancelled", Snackbar.LENGTH_LONG).show()
            setResult(RESULT_OK)
            finish()
        }
    }
}

//                for (i in app.items.indices) {
//                    i("Item[$i]:${this.app.items[i]}")
//                }