package wit.assignments.randomgeneratorapp.activities
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber.i
import wit.assignments.randomgeneratorapp.R
import wit.assignments.randomgeneratorapp.databinding.FragmentEntityListBinding
import wit.assignments.randomgeneratorapp.databinding.CardEntityBinding
import wit.assignments.randomgeneratorapp.models.EntityMemStore
import wit.assignments.randomgeneratorapp.models.EntityModel

class EntityListFragment : Fragment(R.layout.fragment_entity_list), EntityListener {

    lateinit var entities : EntityMemStore
    private var _binding: FragmentEntityListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEntityListBinding.inflate(inflater, container, false)

        entities = arguments?.getSerializable("Entities") as EntityMemStore
        i(entities.toString())
        binding.recyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this.context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = EntityAdapter(entities.findAll(),this)

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

    override fun onOptionsItemSelected(entity: MenuItem): Boolean {
        when (entity.itemId) {
            R.id.add_entity_bag -> {
                val intent = Intent(activity, EntityActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(entity)
    }

    override fun onEntityClick(entity: EntityModel) {
        val intent = Intent(activity, EntityActivity::class.java)
        intent.putExtra("entity_edit", entity as Parcelable)
        intent.putExtra("Entities", entities)
        startActivity(intent)
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

//    var x1 : Float = 0.0f
//    var x2 : Float = 0.0f
//    var y1 : Float = 0.0f
//    var y2 : Float = 0.0f
//
//    override fun onTouchEvent(touchEvent : MotionEvent): Boolean {
//        if(touchEvent.getAction() == MotionEvent.ACTION_DOWN){
//            x1 = touchEvent.getX()
//            y1 = touchEvent.getY()
//        }
//        else if(touchEvent.getAction() == MotionEvent.ACTION_UP) {
//            x2 = touchEvent.getX()
//            y2 = touchEvent.getY()
//        }
//
//        if(x1 < x2 - 20){
//            val launcherIntent = Intent(this, BagListActivity::class.java)
//            startActivityForResult(launcherIntent,0)
//        }else if(x1 > x2 + 20){
//            val launcherIntent = Intent(this, EntityListActivity::class.java)
//            startActivityForResult(launcherIntent,0)
//        }
//        return false;
//    }