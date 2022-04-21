package com.nutrikares.nutrideskapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.databinding.FragmentHomeBinding
import com.nutrikares.nutrideskapp.utils.Calendar

class FragmentHome : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userStats = Datasource.getUserInfo().stats
        
        // Bindings
        binding.weightDisplay.text = resources.getString(R.string.weight_placeholder, userStats.weight)
        binding.imcDisplay.text = resources.getString(R.string.imc_placeholder, userStats.imc)
        binding.todaysRoutine.setOnClickListener {
            val action = FragmentHomeDirections.actionFragmentHomeToFragmentExerciseHome()
            findNavController().navigate(action)
        }
        binding.todaysMenu.setOnClickListener {
            val weekDay = Calendar().getDate("es")
            val action = FragmentHomeDirections.actionFragmentHomeToFragmentFoodDailyHome(dayIndex = weekDay)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}