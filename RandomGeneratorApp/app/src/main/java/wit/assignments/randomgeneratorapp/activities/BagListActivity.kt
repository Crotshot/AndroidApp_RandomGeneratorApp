package wit.assignments.randomgeneratorapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import wit.assignments.randomgeneratorapp.R
import wit.assignments.randomgeneratorapp.databinding.ActivityBagListBinding
import wit.assignments.randomgeneratorapp.databinding.CardBagBinding
import wit.assignments.randomgeneratorapp.main.MainApp
import wit.assignments.randomgeneratorapp.models.BagModel
import wit.assignments.randomgeneratorapp.models.EntityModel
import androidx.fragment.app.Fragment

class BagListActivity : AppCompatActivity(), BagListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityBagListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBagListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = BagAdapter(app.bags.findAll(),this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
    }
    override fun onOptionsItemSelected(bag: MenuItem): Boolean {
        when (bag.itemId) {
            R.id.add_entity_bag -> {
                val launcherIntent = Intent(this, BagActivity::class.java)
                startActivityForResult(launcherIntent,0)
            }
        }
        return super.onOptionsItemSelected(bag)
    }

    override fun onBagClick(bag: BagModel) {
        val launcherIntent = Intent(this, BagActivity::class.java)
        launcherIntent.putExtra("bag_edit", bag)
        startActivityForResult(launcherIntent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        binding.recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }
}

interface BagListener {
    fun onBagClick(bag: BagModel)
}

class BagAdapter constructor(private var bags: List<BagModel>, private val listener: BagListener) :
    RecyclerView.Adapter<BagAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardBagBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val bag = bags[holder.adapterPosition]
        holder.bind(bag, listener)
    }

    override fun getItemCount(): Int {
        return bags.size
    }

    class MainHolder(private val binding : CardBagBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(bag: BagModel, listener: BagListener) {
            binding.bagName.text = bag.name
            binding.root.setOnClickListener { listener.onBagClick(bag) }
        }
    }
}