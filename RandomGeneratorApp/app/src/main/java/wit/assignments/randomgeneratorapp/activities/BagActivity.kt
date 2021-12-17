package wit.assignments.randomgeneratorapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import wit.assignments.randomgeneratorapp.R
import wit.assignments.randomgeneratorapp.databinding.ActivityBagBinding
import wit.assignments.randomgeneratorapp.models.BagMemStore
import wit.assignments.randomgeneratorapp.models.BagModel

class BagActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBagBinding
    var bag = BagModel()
    var bags = BagMemStore()
//    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        app = application as MainApp
        var edit = false

        bags = intent.getSerializableExtra("Bags") as BagMemStore

        binding = ActivityBagBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra("bag_edit")) {
            edit = true
            bag = intent.extras?.getParcelable("bag_edit")!!

            binding.bagName.setText(bag.name)
            binding.addBag.setText(R.string.button_saveEntity)
        }

        binding.addBag.setOnClickListener {
            bag.name = binding.bagName.text.toString()

            //TODO ---------Check item count is at least 2

            if (bag.name.isNotEmpty()) {
                if (edit) {
                    bags.update(bag.copy())
                } else {
                    bags.create(bag.copy())
                }
                var resultIntent : Intent = Intent()
                resultIntent.putExtra("Bags", bags)
                setResult(AppCompatActivity.RESULT_OK, resultIntent)
                finish()
            } else {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    R.string.enter_bag_data,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

        binding.deleteBag.setOnClickListener {
            bags.delete(bag)

            var resultIntent : Intent = Intent()
            resultIntent.putExtra("Bags", bags)
            setResult(AppCompatActivity.RESULT_OK, resultIntent)
            finish()
        }

        binding.cancelBag.setOnClickListener {
            Snackbar.make(
                findViewById(android.R.id.content),
                "Item Addition Cancelled",
                Snackbar.LENGTH_LONG
            ).show()
            setResult(AppCompatActivity.RESULT_CANCELED)
            finish()
        }
    }
}