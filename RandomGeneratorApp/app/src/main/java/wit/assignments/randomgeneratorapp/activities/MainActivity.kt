package wit.assignments.randomgeneratorapp.activities
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.os.PersistableBundle
import androidx.fragment.app.Fragment
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.firebase.analytics.FirebaseAnalytics
import timber.log.Timber
import timber.log.Timber.i
import wit.assignments.randomgeneratorapp.R
import wit.assignments.randomgeneratorapp.databinding.ActivityMainBinding
import wit.assignments.randomgeneratorapp.main.MainApp
import wit.assignments.randomgeneratorapp.models.BagMemStore
import wit.assignments.randomgeneratorapp.models.BagModel
import wit.assignments.randomgeneratorapp.models.EntityMemStore
import wit.assignments.randomgeneratorapp.models.EntityModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var analytics: FirebaseAnalytics

    lateinit var app: MainApp
    var entities = EntityMemStore()
    var bags = BagMemStore()

    private val entityBundle = Bundle()
    private val bagBundle = Bundle()

    val entityFragment = EntityListFragment()
    val bagFragment = BagListFragment()
    val testFragment1 = TestFragment_1()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("---------------------------Main Activity Created")
        bags.create(BagModel(1234,"banana"))
        bags.create(BagModel(42345,"Egg"))
        bags.create(BagModel(4432435,"Yo"))
        bags.create(BagModel(456885,"Hello"))
        entities.create(EntityModel(3123, "Entity", 5.9093f))
        entities.create(EntityModel(3244343, "Fragment", 24553.093f))
        entities.create(EntityModel(334443556743, "has", 45343.9093f))
        entities.create(EntityModel(3123, "been", 23.90343f))
        entities.create(EntityModel(7567523, "implemented", 234.906f))
        entities.create(EntityModel(678867, "completely", 21567567.9093f))
        entities.create(EntityModel(789789, "Hurray!!!", 567567.9093f))

        setContentView(R.layout.activity_main)
        app = application as MainApp
        analytics = FirebaseAnalytics.getInstance(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        entityBundle.putSerializable("Entities", entities)
        bagBundle.putSerializable("Bags", bags)

        bagFragment.arguments = bagBundle
        entityFragment.arguments = entityBundle

        setCurrentFragment(testFragment1)//,2)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.show_bag_view->setCurrentFragment(bagFragment)//, 3)
                R.id.show_entity_view->setCurrentFragment(entityFragment)//, 2)
                R.id.show_generate_view->setCurrentFragment(testFragment1)//, 1)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment:Fragment/*, num : Int*/) =  supportFragmentManager.beginTransaction().apply {
        analytics.logEvent("Fragment_Swapped", null)
        replace(R.id.flFragment, fragment, "CurrentFrag")
        commit()
    }

    fun BagEditor(bag: BagModel?){
        val intent = Intent(this, BagActivity::class.java)
        intent.putExtra("Bags", bags)
        if(bag != null){
            intent.putExtra("bag_edit", bag as Parcelable)
        }
        i("=-=--=-=-=--=-=-=--=-=--==--==-=-=-=-=-==-=-=-=-> Creating/Editing a Bag <-=--=-=-=--=-=-=--=-=--==--==-=-=-=-=-==-=-=-=-")
        startActivityForResult(intent,0)//getActivity(this).
    }

    fun EntityEditor(entity: EntityModel?){
        val intent = Intent(this, EntityActivity::class.java)
        intent.putExtra("Entities", entities)
        if(entity != null){
            intent.putExtra("entity_edit", entity as Parcelable)
        }
        i("=-=--=-=-=--=-=-=--=-=--==--==-=-=-=-=-==-=-=-=-> Creating/Editing a Entity <-=--=-=-=--=-=-=--=-=--==--==-=-=-=-=-==-=-=-=-")
        startActivityForResult(intent,1)//getActivity(this).
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        i("Activity result recieved")
        if(requestCode == 0){
            bags = data?.getSerializableExtra("Bags") as BagMemStore
            bagBundle.putSerializable("Bags", bags)
            bagFragment.arguments = bagBundle
            setCurrentFragment(bagFragment)
        }else if(requestCode == 1){
            entities = data?.getSerializableExtra("Entities") as EntityMemStore
            entityBundle.putSerializable("Entities", entities)
            entityFragment.arguments = entityBundle
            setCurrentFragment(entityFragment)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

//    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
////        super.onSaveInstanceState(outState, outPersistentState)
//    }
}