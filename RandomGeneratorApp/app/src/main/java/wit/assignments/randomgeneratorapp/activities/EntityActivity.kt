package wit.assignments.randomgeneratorapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import wit.assignments.randomgeneratorapp.R
import wit.assignments.randomgeneratorapp.databinding.ActivityEntityBinding
import wit.assignments.randomgeneratorapp.models.EntityModel
import wit.assignments.randomgeneratorapp.main.MainApp
import wit.assignments.randomgeneratorapp.models.BagMemStore
import wit.assignments.randomgeneratorapp.models.EntityMemStore
import java.io.Serializable

class EntityActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEntityBinding

    var entity = EntityModel()
    var entities = EntityMemStore()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var edit = false

        entities = intent.getSerializableExtra("Entities") as EntityMemStore

        binding = ActivityEntityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra("entity_edit")) {
            edit = true
            entity = intent.extras?.getParcelable("entity_edit")!!

            binding.entityName.setText(entity.name)
            binding.entityWeight.setText(entity.weight.toString())
            binding.addEntity.setText(R.string.button_saveEntity)
        }

        binding.addEntity.setOnClickListener {
            entity.name = binding.entityName.text.toString()
            entity.weight = 0f
            if(binding.entityWeight.text.toString().isNotEmpty())
                entity.weight = binding.entityWeight.text.toString().toFloat()
            if (entity.name.isNotEmpty() && entity.weight > 0) {
                if (edit) {
                    entities.update(entity.copy())
                } else {
                    entities.create(entity.copy())
                }
                var resultIntent : Intent = Intent()
                resultIntent.putExtra("Entities", entities)
                setResult(AppCompatActivity.RESULT_OK, resultIntent)
                finish()
            } else {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    R.string.enter_entity_data,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

        binding.deleteEntity.setOnClickListener {

            entities.delete(entity)

            var resultIntent : Intent = Intent()
            resultIntent.putExtra("Entities", entities)
            setResult(AppCompatActivity.RESULT_OK, resultIntent)
            finish()
        }

        binding.cancelEntity.setOnClickListener {
            Snackbar.make(
                findViewById(android.R.id.content),
                "Item Addition Cancelled",
                Snackbar.LENGTH_LONG
            ).show()
            setResult(RESULT_OK)
            finish()
        }
    }
}