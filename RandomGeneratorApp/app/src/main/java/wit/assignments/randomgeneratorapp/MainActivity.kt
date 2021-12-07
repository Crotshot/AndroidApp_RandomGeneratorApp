package wit.assignments.randomgeneratorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import wit.assignments.randomgeneratorapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.GREETINGS.setOnClickListener {
            val greetingText = getString(R.string.greeting_text)
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, greetingText, duration)
            toast.show()
        }

    }
}