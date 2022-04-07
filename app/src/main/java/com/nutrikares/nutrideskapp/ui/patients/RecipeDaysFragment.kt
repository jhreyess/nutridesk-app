package com.nutrikares.nutrideskapp.ui.patients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nutrikares.nutrideskapp.R
import com.nutrikares.nutrideskapp.databinding.FragmentRecipeDaysBinding
import com.nutrikares.nutrideskapp.databinding.FragmentViewPatientBinding

class RecipeDaysFragment : Fragment() {
    private var _binding: FragmentRecipeDaysBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipeDaysBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.sundayRecipeCard.setOnClickListener {
            findNavController().navigate(R.id.action_recipeDaysFragment_to_dayRecipeFragment)
        }
        binding.mondayRecipeCard.setOnClickListener {
            findNavController().navigate(R.id.action_recipeDaysFragment_to_dayRecipeFragment)
        }
        binding.tuesdayRecipeCard.setOnClickListener {
            findNavController().navigate(R.id.action_recipeDaysFragment_to_dayRecipeFragment)
        }
        binding.wednesdayRecipeCard.setOnClickListener {
            findNavController().navigate(R.id.action_recipeDaysFragment_to_dayRecipeFragment)
        }
        binding.thursdayRecipeCard.setOnClickListener {
            findNavController().navigate(R.id.action_recipeDaysFragment_to_dayRecipeFragment)
        }
        binding.fridayRecipeCard.setOnClickListener {
            findNavController().navigate(R.id.action_recipeDaysFragment_to_dayRecipeFragment)
        }
        binding.saturdayRecipeCard.setOnClickListener {
            findNavController().navigate(R.id.action_recipeDaysFragment_to_dayRecipeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}