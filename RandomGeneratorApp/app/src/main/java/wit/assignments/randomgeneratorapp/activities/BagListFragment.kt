package wit.assignments.randomgeneratorapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import wit.assignments.randomgeneratorapp.R
import wit.assignments.randomgeneratorapp.databinding.FragmentBagListBinding
import wit.assignments.randomgeneratorapp.databinding.CardBagBinding
import wit.assignments.randomgeneratorapp.models.BagModel
import androidx.fragment.app.Fragment
import timber.log.Timber.i
import wit.assignments.randomgeneratorapp.models.BagMemStore

class BagListFragment : Fragment(R.layout.fragment_bag_list), BagListener {

    lateinit var bags : BagMemStore
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

        bags = arguments?.getSerializable("Bags") as BagMemStore
        i(bags.toString())

        setHasOptionsMenu(true);
        //val recyclerView = activity!!.findViewById<View>(R.id.recyclerView) as RecyclerView
        binding.recyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this.context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = BagAdapter(bags.findAll(),this)

        //return super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

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
                (activity as MainActivity?)?.BagEditor(null)
            }
        }
        return super.onOptionsItemSelected(bag)
    }

    override fun onBagClick(bag: BagModel) {
        (activity as MainActivity?)?.BagEditor(bag)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
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
