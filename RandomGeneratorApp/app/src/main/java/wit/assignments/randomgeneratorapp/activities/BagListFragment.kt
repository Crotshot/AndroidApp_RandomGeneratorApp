package wit.assignments.randomgeneratorapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import wit.assignments.randomgeneratorapp.R
import wit.assignments.randomgeneratorapp.databinding.FragmentBagListBinding
import wit.assignments.randomgeneratorapp.databinding.CardBagBinding
import wit.assignments.randomgeneratorapp.main.MainApp
import wit.assignments.randomgeneratorapp.models.BagModel
import androidx.fragment.app.Fragment
import timber.log.Timber.i

class BagListFragment : Fragment(R.layout.fragment_bag_list), BagListener {

    lateinit var app: MainApp
    private var _binding: FragmentBagListBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBagListBinding.inflate(inflater, container, false)

        app = arguments?.getSerializable("MainApp") as MainApp
        i(app.toString())


        //val recyclerView = activity!!.findViewById<View>(R.id.recyclerView) as RecyclerView
        binding.recyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this.context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = BagAdapter(app.bags.findAll(),this)

        //return super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return super.onCreateOptionsMenu(menu)
//    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

//    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
//        super.onCreateContextMenu(menu, v, menuInfo)
//    }
    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onOptionsItemSelected(bag: MenuItem): Boolean {
        when (bag.itemId) {
            R.id.add_entity_bag -> {
//                val launcherIntent = Intent(this, BagActivity::class.java)
//                startActivityForResult(launcherIntent,0)
                val intent = Intent(activity, BagActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(bag)
    }

    override fun onBagClick(bag: BagModel) {
        val intent = Intent(activity, BagActivity::class.java)
        intent.putExtra("bag_edit", bag)
        startActivity(intent)

//        val launcherIntent = Intent(this, BagActivity::class.java)
//        launcherIntent.putExtra("bag_edit", bag)
//        startActivityForResult(launcherIntent, 0)
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
//    companion object {
//        fun makeInstance(mainApp: MainApp): BagListFragment? {
//            val fragment = BagListFragment()
//            val bundle = Bundle()
//            bundle.putSerializable("MainApp", mainApp)
//            fragment.arguments = bundle
//
////            app = arguments?.getSerializable("MainApp") as MainApp
////            binding.recyclerView.adapter = BagAdapter(app.bags.findAll(), this)
////        binding = FragmentBagListBinding.inflate(layoutInflater)
////        val layoutManager = LinearLayoutManager(this.context)
////        binding.recyclerView.layoutManager = layoutManager
////        binding.recyclerView.adapter = BagAdapter(app.bags.findAll(),this)
//            return fragment
//        }
//    }
