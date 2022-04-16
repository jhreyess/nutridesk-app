package com.nutrikares.nutrideskapp.ui.patients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nutrikares.nutrideskapp.R
import com.nutrikares.nutrideskapp.databinding.FragmentRoutineDaysBinding
import com.nutrikares.nutrideskapp.databinding.FragmentViewPatientBinding

class RoutineDaysFragment : Fragment() {

    private var _binding: FragmentRoutineDaysBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRoutineDaysBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.sundayRoutineCard.setOnClickListener {
            findNavController().navigate(R.id.action_routineDaysFragment_to_assignRoutineFragment)
        }
        binding.mondayRoutineCard.setOnClickListener {
            findNavController().navigate(R.id.action_routineDaysFragment_to_assignRoutineFragment)
        }
        binding.tuesdayRoutineCard.setOnClickListener {
            findNavController().navigate(R.id.action_routineDaysFragment_to_assignRoutineFragment)
        }
        binding.wednesdayRoutineCard.setOnClickListener {
            findNavController().navigate(R.id.action_routineDaysFragment_to_assignRoutineFragment)
        }
        binding.thursdayRoutineCard.setOnClickListener {
            findNavController().navigate(R.id.action_routineDaysFragment_to_assignRoutineFragment)
        }
        binding.fridayRoutineCard.setOnClickListener {
            findNavController().navigate(R.id.action_routineDaysFragment_to_assignRoutineFragment)
        }
        binding.saturdayRoutineCard.setOnClickListener {
            findNavController().navigate(R.id.action_routineDaysFragment_to_assignRoutineFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}