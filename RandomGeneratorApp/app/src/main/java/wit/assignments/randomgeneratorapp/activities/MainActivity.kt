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

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val testFragment2 = TestFragment_2()
        val testFragment1 = TestFragment_1()
        val testFragment3 = TestFragment_3()

        setCurrentFragment(testFragment2)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.show_bag_view->setCurrentFragment(testFragment1)
                R.id.show_entity_view->setCurrentFragment(testFragment2)
                R.id.show_generate_view->setCurrentFragment(testFragment3)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment:Fragment) =  supportFragmentManager.beginTransaction().apply {

            replace(R.id.flFragment,fragment)
            commit()
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