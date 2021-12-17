package wit.assignments.randomgeneratorapp.activities
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
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
    val generateFragment = GenerateSettingsFragment()

    private lateinit var database : FirebaseDatabase
    private lateinit var dataref : DatabaseReference
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("---------------------------Main Activity Created")
//        bags.create(BagModel(1234,"A"))
//        bags.create(BagModel(42345,"B"))
//        bags.create(BagModel(4432435,"C"))
//        bags.create(BagModel(456885,"D"))
//        entities.create(EntityModel(3123, "E", 5.9093f))
//        entities.create(EntityModel(3244343, "F", 24553.093f)) //DUMMY TEST DATA
//        entities.create(EntityModel(334443556743, "G", 45343.9093f))
//        entities.create(EntityModel(3123, "H", 23.90343f))
//        entities.create(EntityModel(7567523, "I", 234.906f))
//        entities.create(EntityModel(678867, "J", 21567567.9093f))
//        entities.create(EntityModel(789789, "K", 567567.9093f))

        setContentView(R.layout.activity_main)
        app = application as MainApp
        analytics = FirebaseAnalytics.getInstance(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        entityBundle.putSerializable("Entities", entities)
        bagBundle.putSerializable("Bags", bags)

        bagFragment.arguments = bagBundle
        entityFragment.arguments = entityBundle

        setCurrentFragment(generateFragment)//,2)

        database = FirebaseDatabase.getInstance("https://randomgeneratorapp-default-rtdb.europe-west1.firebasedatabase.app")
        dataref = database.getReference()

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.show_bag_view->setCurrentFragment(bagFragment)//, 3)
                R.id.show_entity_view->setCurrentFragment(entityFragment)//, 2)
                R.id.show_generate_view->setCurrentFragment(generateFragment)//, 1)
            }
            true
        }

        val intent = Intent(this, SignInActivity::class.java)
        i("=-=--=-=-=--=-=-=--=-=--==--==-=-=-=-=-==-=-=-=-> Going to Sign in View <-=--=-=-=--=-=-=--=-=--==--==-=-=-=-=-==-=-=-=-")
        startActivityForResult(intent,3)
    }

    private fun setCurrentFragment(fragment:Fragment/*, num : Int*/) =  supportFragmentManager.beginTransaction().apply {
        analytics.logEvent("Fragment_Swapped", null)
        replace(R.id.flFragment, fragment, "CurrentFrag")
        commitAllowingStateLoss()
    //commit()
    }

    fun BagEditor(bag: BagModel?){
        val intent = Intent(this, BagActivity::class.java)
        intent.putExtra("Bags", bags)
        if(bag != null){
            intent.putExtra("bag_edit", bag as Parcelable)
        }
        i("=-=--=-=-=--=-=-=--=-=--==--==-=-=-=-=-==-=-=-=-> Creating/Editing a Bag <-=--=-=-=--=-=-=--=-=--==--==-=-=-=-=-==-=-=-=-")
        startActivityForResult(intent,0)
    }

    fun EntityEditor(entity: EntityModel?){
        val intent = Intent(this, EntityActivity::class.java)
        intent.putExtra("Entities", entities)
        if(entity != null){
            intent.putExtra("entity_edit", entity as Parcelable)
        }
        i("=-=--=-=-=--=-=-=--=-=--==--==-=-=-=-=-==-=-=-=-> Creating/Editing a Entity <-=--=-=-=--=-=-=--=-=--==--==-=-=-=-=-==-=-=-=-")
        startActivityForResult(intent,1)
    }

    fun GenerateView(){
        val intent = Intent(this, GenerateActivity::class.java)
        intent.putExtra("Entities", entities)
        intent.putExtra("Bags", bags)
        i("=-=--=-=-=--=-=-=--=-=--==--==-=-=-=-=-==-=-=-=-> Going to Generation View <-=--=-=-=--=-=-=--=-=--==--==-=-=-=-=-==-=-=-=-")
        startActivityForResult(intent,2)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        i("Activity result received")
        var fragTrans : FragmentTransaction = supportFragmentManager.beginTransaction()
        if(data != null){
            if(requestCode == 0){ //Returning from Bag Activity
                bags = data?.getSerializableExtra("Bags") as BagMemStore
                bagBundle.putSerializable("Bags", bags)
                bagFragment.arguments!!.putAll(bagBundle)
                fragTrans.detach(bagFragment)
                fragTrans.attach(bagFragment)
                saveToDatabase(entities,bags,userId)
            }else if(requestCode == 1){ //Returning from Entity Activity
                entities = data?.getSerializableExtra("Entities") as EntityMemStore
                entityBundle.putSerializable("Entities", entities)
                entityFragment.arguments!!.putAll(entityBundle)
                fragTrans.detach(entityFragment)
                fragTrans.attach(entityFragment)
                saveToDatabase(entities,bags,userId)
            }else if(requestCode == 2){ //Returning from Generate Activity
                //Nothing is changed upon returning from Generate Activity, this is here just in case that changes in future
            }
            else if(requestCode == 3){
                userId = data?.getStringExtra("userId").toString()

                entities.deleteAll(entities)
                bags.deleteAll(bags)

                if (userId != null) {
                    dataref.child("data").child(userId).child("ents").get().addOnSuccessListener {
                        var entityArr = ArrayList<EntityModel>()
                        for(entity in it.children){
                            var entityModel = EntityModel()
                            entityModel.id = entity.child("id").value.toString().toLong()
                            entityModel.name = entity.child("name").value.toString()
                            entityModel.weight = entity.child("weight").value.toString().toFloat()
                            entityArr.add(entityModel)
                        }
                        entities.replace(entityArr)
                    }
                    dataref.child("data").child(userId).child("bags").get().addOnSuccessListener {
                        var bagArr = ArrayList<BagModel>()
                        for(bag in it.children){
                            var bagModel = BagModel()
                            bagModel.id = bag.child("id").value.toString().toLong()
                            bagModel.name = bag.child("name").value.toString()

                            bagArr.add(bagModel)
                        }
                        bags.replace(bagArr)
                    }
                }

                bagBundle.putSerializable("Bags", bags)
                bagFragment.arguments!!.putAll(bagBundle)

                entityBundle.putSerializable("Entities", entities)
                entityFragment.arguments!!.putAll(entityBundle)
            }
        }
        fragTrans.commitAllowingStateLoss()
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun saveToDatabase(entities: EntityMemStore, bags: BagMemStore, userId: String){
        var count  = 0
        for(ent in entities.findAll()){
            dataref.child("data").child(userId).child("ents").child(count.toString()).setValue(ent).addOnCompleteListener{i("Saved user entities")}
            count++
        }
        count = 0
        for(bag in bags.findAll()){
            dataref.child("data").child(userId).child("bags").child(count.toString()).setValue(bag).addOnCompleteListener{i("Saved user bags")}
            count++
        }
    }
}