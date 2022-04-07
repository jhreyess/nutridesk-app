package com.nutrikares.nutrideskapp.ui.patients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nutrikares.nutrideskapp.R
import com.nutrikares.nutrideskapp.databinding.FragmentDayRecipeBinding
import com.nutrikares.nutrideskapp.databinding.FragmentRecipeDaysBinding

class DayRecipeFragment : Fragment() {
    private var _binding: FragmentDayRecipeBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDayRecipeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.breakfastRecipeCard.setOnClickListener{
            findNavController().navigate(R.id.action_dayRecipeFragment_to_assingRecipeFragment)
        }
        binding.mealRecipeCard.setOnClickListener{
            findNavController().navigate(R.id.action_dayRecipeFragment_to_assingRecipeFragment)
        }
        binding.dinnerRecipeCard.setOnClickListener{
            findNavController().navigate(R.id.action_dayRecipeFragment_to_assingRecipeFragment)
        }
        binding.firstSnackRecipeCard.setOnClickListener{
            findNavController().navigate(R.id.action_dayRecipeFragment_to_assingRecipeFragment)
        }
        binding.secondSnackRecipeCard.setOnClickListener{
            findNavController().navigate(R.id.action_dayRecipeFragment_to_assingRecipeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}