package wit.assignments.randomgeneratorapp.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import wit.assignments.randomgeneratorapp.R
import wit.assignments.randomgeneratorapp.databinding.FragmentGenerateSettingsBinding

class GenerateSettingsFragment : Fragment(R.layout.fragment_generate_settings){
    private var _binding: FragmentGenerateSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGenerateSettingsBinding.inflate(inflater, container, false)

        binding.generate.setOnClickListener {
            (activity as MainActivity?)?.GenerateView()
        }


        return binding.root
    }
}

