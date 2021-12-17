package wit.assignments.randomgeneratorapp.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import wit.assignments.randomgeneratorapp.databinding.ActivityGenerateBinding
import wit.assignments.randomgeneratorapp.databinding.CardEntityBinding
import wit.assignments.randomgeneratorapp.models.BagMemStore
import wit.assignments.randomgeneratorapp.models.EntityMemStore
import wit.assignments.randomgeneratorapp.models.EntityModel

class GenerateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGenerateBinding
    var bags = BagMemStore()
    var entities = EntityMemStore()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bags = intent.getSerializableExtra("Bags") as BagMemStore
        entities = intent.getSerializableExtra("Entities") as EntityMemStore

        binding = ActivityGenerateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRecyclerView(GenerateRandomList())
        binding.generate.setOnClickListener {
            setRecyclerView(GenerateRandomList())
        }

        binding.leave.setOnClickListener {
            Snackbar.make(
                findViewById(android.R.id.content),
                "Exiting Generation View",
                Snackbar.LENGTH_LONG
            ).show()
            setResult(AppCompatActivity.RESULT_CANCELED)
            finish()
        }
    }

    fun GenerateRandomList() : EntityMemStore {
        return EntityMemStore()
    }

    fun setRecyclerView(entityMemStore: EntityMemStore){
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = GenerateAdapter(entityMemStore.findAll())
    }
}

class GenerateAdapter constructor(private var entities: List<EntityModel>) :
    RecyclerView.Adapter<GenerateAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardEntityBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val entity = entities[holder.adapterPosition]
        holder.bind(entity)
    }

    override fun getItemCount(): Int = entities.size

    class MainHolder(private val binding : CardEntityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(entity: EntityModel) {
            binding.entityName.text = entity.name
            binding.description.text = entity.weight.toString()
        }
    }
}