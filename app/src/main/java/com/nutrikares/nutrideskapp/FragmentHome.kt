package com.nutrikares.nutrideskapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.databinding.FragmentHomeBinding
import java.util.*

class FragmentHome : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val date = {x: Int -> when(x){
        Calendar.MONDAY -> "Lunes"
        Calendar.TUESDAY -> "Martes"
        Calendar.WEDNESDAY -> "Miércoles"
        Calendar.THURSDAY -> "Jueves"
        Calendar.FRIDAY -> "Viernes"
        Calendar.SATURDAY -> "Sábado"
        Calendar.SUNDAY -> "Domingo"
        else -> "Undefined"
    } }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Bindings
        binding.weightDisplay.text = resources.getString(R.string.weight_placeholder, Datasource.userWeight)
        binding.imcDisplay.text = resources.getString(R.string.imc_placeholder, Datasource.userIMC)
        binding.todaysRoutine.setOnClickListener {
            val action = FragmentHomeDirections.actionFragmentHomeToFragmentExerciseHome()
            findNavController().navigate(action)
        }
        binding.todaysMenu.setOnClickListener {
            val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
            val action = FragmentHomeDirections.actionFragmentHomeToFragmentFoodDailyHome(dayIndex = date(today))
            findNavController().navigate(action)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}