package wit.assignments.randomgeneratorapp.activities
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import wit.assignments.randomgeneratorapp.R
import wit.assignments.randomgeneratorapp.databinding.ActivityEntityListBinding
import wit.assignments.randomgeneratorapp.databinding.CardEntityBinding
import wit.assignments.randomgeneratorapp.main.MainApp
import wit.assignments.randomgeneratorapp.models.EntityModel

class EntityListActivity : AppCompatActivity(), EntityListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityEntityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEntityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = EntityAdapter(app.entities.findAll(),this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(entity: MenuItem): Boolean {
        when (entity.itemId) {
            R.id.add_entity_bag -> {
                val launcherIntent = Intent(this, EntityActivity::class.java)
                startActivityForResult(launcherIntent,0)
            }
        }
        return super.onOptionsItemSelected(entity)
    }

    override fun onEntityClick(entity: EntityModel) {
        val launcherIntent = Intent(this, EntityActivity::class.java)
        launcherIntent.putExtra("entity_edit", entity)
        startActivityForResult(launcherIntent,0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        binding.recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }
}

interface EntityListener {
    fun onEntityClick(entity: EntityModel)
}

class EntityAdapter constructor(private var entities: List<EntityModel>, private val listener: EntityListener) :
    RecyclerView.Adapter<EntityAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardEntityBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val entity = entities[holder.adapterPosition]
        holder.bind(entity, listener)
    }

    override fun getItemCount(): Int = entities.size

    class MainHolder(private val binding : CardEntityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(entity: EntityModel, listener: EntityListener) {
            binding.entityName.text = entity.name
            binding.description.text = entity.weight.toString()
            binding.root.setOnClickListener { listener.onEntityClick(entity) }
        }
    }
}