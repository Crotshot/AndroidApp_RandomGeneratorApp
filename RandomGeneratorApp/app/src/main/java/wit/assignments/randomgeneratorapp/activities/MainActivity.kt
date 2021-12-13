package wit.assignments.randomgeneratorapp.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import timber.log.Timber.i
import wit.assignments.randomgeneratorapp.R
import wit.assignments.randomgeneratorapp.databinding.ActivityMainBinding
import wit.assignments.randomgeneratorapp.main.MainApp
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        app = application as MainApp

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val bundle = Bundle()
        bundle.putSerializable("MainApp", app)

        val entityFragment = EntityListFragment()
        var bagFragment = BagListFragment()
        val testFragment1 = TestFragment_1()

        bagFragment.arguments = bundle
        entityFragment.arguments = bundle


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
//            if(num == 3) {
//                var frag: Fragment = BagListFragment.makeInstance(app) as Fragment
//                replace(R.id.flFragment, frag)
//                commit()
//            }
//        else {
                replace(R.id.flFragment, fragment)
                commit()
//            }
        }

//    private var _binding: ActivityMainBinding? = null
//    private val binding get() = _binding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = ActivityMainBinding.inflate(inflater, container, false)
//        val view = binding.root
//        return view
//    }
//
//    override fun onCreateView(
//        name: String,
//        context: Context,
//        attrs: AttributeSet)
//    : View? {
//        return super.onCreateView(name, context, attrs)
//    }
//
//    override fun onCreateView(
//        parent: View?,
//        name: String,
//        context: Context,
//        attrs: AttributeSet
//    ): View? {
//        return super.onCreateView(parent, name, context, attrs)
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        _binding = null
//    }
}