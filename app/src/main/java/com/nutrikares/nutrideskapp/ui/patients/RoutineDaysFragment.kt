package com.nutrikares.nutrideskapp.ui.patients

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nutrikares.nutrideskapp.R
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.data.models.Routine
import com.nutrikares.nutrideskapp.databinding.FragmentRoutineDaysBinding

class RoutineDaysFragment : Fragment() {

    private var _binding: FragmentRoutineDaysBinding? = null
    private val binding get() = _binding!!

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
        val user = Datasource.getCurrentUser()
        val userRoutines :MutableMap<String, Routine> = user.routines
        val sundayRoutine = userRoutines["sunday"]?.name.toString()
        val mondayRoutine  = userRoutines["monday"]?.name.toString()
        val tuesdayRoutine  = userRoutines["tuesday"]?.name.toString()
        val wednesdayRoutine  = userRoutines["wednesday"]?.name.toString()
        val thursdayRoutine  = userRoutines["thursday"]?.name.toString()
        val fridayRoutine = userRoutines["friday"]?.name.toString()
        val saturdayRoutine = userRoutines["saturday"]?.name.toString()

        binding.sundayRoutineTextView.text = if(sundayRoutine.equals("")) resources.getString(R.string.noAssigned) else sundayRoutine
        binding.mondayRoutineTextView.text = if(mondayRoutine.equals("")) resources.getString(R.string.noAssigned) else mondayRoutine
        binding.tuesdayRoutineTextView.text = if(tuesdayRoutine.equals("")) resources.getString(R.string.noAssigned) else tuesdayRoutine
        binding.wednesdayRoutineTextView.text = if(wednesdayRoutine.equals("")) resources.getString(R.string.noAssigned) else wednesdayRoutine
        binding.thursdayRoutineTextView.text = if(thursdayRoutine.equals("")) resources.getString(R.string.noAssigned) else thursdayRoutine
        binding.fridayRoutineTextView.text = if(fridayRoutine.equals("")) resources.getString(R.string.noAssigned) else fridayRoutine
        binding.saturdayRoutineTextView.text = if(saturdayRoutine.equals("")) resources.getString(R.string.noAssigned) else saturdayRoutine

        binding.sundayRoutineCard.setOnClickListener {
            Datasource.routineDaySelected = "sunday"
            findNavController().navigate(R.id.action_routineDaysFragment_to_assignRoutineFragment)
        }
        binding.mondayRoutineCard.setOnClickListener {
            Datasource.routineDaySelected = "monday"
            findNavController().navigate(R.id.action_routineDaysFragment_to_assignRoutineFragment)
        }
        binding.tuesdayRoutineCard.setOnClickListener {
            Datasource.routineDaySelected = "tuesday"
            findNavController().navigate(R.id.action_routineDaysFragment_to_assignRoutineFragment)
        }
        binding.wednesdayRoutineCard.setOnClickListener {
            Datasource.routineDaySelected = "wednesday"
            findNavController().navigate(R.id.action_routineDaysFragment_to_assignRoutineFragment)
        }
        binding.thursdayRoutineCard.setOnClickListener {
            Datasource.routineDaySelected = "thursday"
            findNavController().navigate(R.id.action_routineDaysFragment_to_assignRoutineFragment)
        }
        binding.fridayRoutineCard.setOnClickListener {
            Datasource.routineDaySelected = "friday"
            findNavController().navigate(R.id.action_routineDaysFragment_to_assignRoutineFragment)
        }
        binding.saturdayRoutineCard.setOnClickListener {
            Datasource.routineDaySelected = "saturday"
            findNavController().navigate(R.id.action_routineDaysFragment_to_assignRoutineFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}