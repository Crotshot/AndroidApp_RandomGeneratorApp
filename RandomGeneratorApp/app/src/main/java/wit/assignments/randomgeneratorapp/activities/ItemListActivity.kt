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
import wit.assignments.randomgeneratorapp.databinding.ActivityItemListBinding
import wit.assignments.randomgeneratorapp.databinding.CardItemBinding
import wit.assignments.randomgeneratorapp.main.MainApp
import wit.assignments.randomgeneratorapp.models.ItemModel

class ItemListActivity : AppCompatActivity(), ItemListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityItemListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemListBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        binding.toolbar.title = title
//        setSupportActionBar(binding.toolbar) //Crashes the program
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = ItemAdapter(app.items.findAll(),this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, ItemActivity::class.java)
                startActivityForResult(launcherIntent,0)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(item: ItemModel) {
        val launcherIntent = Intent(this, ItemActivity::class.java)
        launcherIntent.putExtra("item_edit", item)
        startActivityForResult(launcherIntent,0)
    }
}

interface ItemListener {
    fun onItemClick(item: ItemModel)
}

class ItemAdapter constructor(private var items: List<ItemModel>,private val listener: ItemListener) :
    RecyclerView.Adapter<ItemAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val item = items[holder.adapterPosition]
        holder.bind(item, listener)
    }

    override fun getItemCount(): Int = items.size

    class MainHolder(private val binding : CardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemModel, listener: ItemListener) {
            binding.itemName.text = item.name
            binding.description.text = item.weight.toString()
            binding.root.setOnClickListener { listener.onItemClick(item) }
        }
    }
}