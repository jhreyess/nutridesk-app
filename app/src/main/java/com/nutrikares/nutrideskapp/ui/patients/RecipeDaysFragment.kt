package com.nutrikares.nutrideskapp.ui.patients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nutrikares.nutrideskapp.R
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.databinding.FragmentRecipeDaysBinding
import com.nutrikares.nutrideskapp.databinding.FragmentViewPatientBinding

class RecipeDaysFragment : Fragment() {
    private var _binding: FragmentRecipeDaysBinding? = null
    private val binding get() = _binding!!

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
            Datasource.recipeDaySelected = "sunday"
            DayRecipeFragment.Day=binding.sundayTextTextView.text.toString()
            findNavController().navigate(R.id.action_recipeDaysFragment_to_dayRecipeFragment)
        }
        binding.mondayRecipeCard.setOnClickListener {
            Datasource.recipeDaySelected = "monday"
            DayRecipeFragment.Day=binding.mondayTextTextView.text.toString()
            findNavController().navigate(R.id.action_recipeDaysFragment_to_dayRecipeFragment)
        }
        binding.tuesdayRecipeCard.setOnClickListener {
            Datasource.recipeDaySelected = "tuesday"
            DayRecipeFragment.Day=binding.tuesdayTextTextView.text.toString()
            findNavController().navigate(R.id.action_recipeDaysFragment_to_dayRecipeFragment)
        }
        binding.wednesdayRecipeCard.setOnClickListener {
            Datasource.recipeDaySelected = "wednesday"
            DayRecipeFragment.Day=binding.wednesdayTextTextView.text.toString()
            findNavController().navigate(R.id.action_recipeDaysFragment_to_dayRecipeFragment)
        }
        binding.thursdayRecipeCard.setOnClickListener {
            Datasource.recipeDaySelected = "thursday"
            DayRecipeFragment.Day=binding.thursdayTextTextView.text.toString()
            findNavController().navigate(R.id.action_recipeDaysFragment_to_dayRecipeFragment)
        }
        binding.fridayRecipeCard.setOnClickListener {
            Datasource.recipeDaySelected = "friday"
            DayRecipeFragment.Day=binding.fridayTextTextView.text.toString()
            findNavController().navigate(R.id.action_recipeDaysFragment_to_dayRecipeFragment)
        }
        binding.saturdayRecipeCard.setOnClickListener {
            Datasource.recipeDaySelected = "saturday"
            DayRecipeFragment.Day=binding.saturdayTextTextView.text.toString()
            findNavController().navigate(R.id.action_recipeDaysFragment_to_dayRecipeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}