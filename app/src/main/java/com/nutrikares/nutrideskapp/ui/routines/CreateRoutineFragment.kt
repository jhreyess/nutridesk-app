package com.nutrikares.nutrideskapp.ui.routines

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nutrikares.nutrideskapp.R
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.data.models.Exercise
import com.nutrikares.nutrideskapp.databinding.FragmentCreateRoutineBinding
import java.lang.Exception

class CreateRoutineFragment : Fragment() {

    private var _binding: FragmentCreateRoutineBinding? = null
    private val binding get() = _binding!!
    private var excercises : MutableList<Exercise> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateRoutineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if(Datasource.getClickOnRoutine()) {
            val routine = Datasource.getCurrentRoutine()
            excercises=routine.exercises
            binding.routineNameEditText.setText(routine.name)
            binding.routineIdEditText.setText(routine.id)
            binding.routineIdEditText.isFocusable = false
            binding.listOfExcercisesTextView.setText(convertListIntoString(excercises))
        }

        binding.addExcerciseImageView.setOnClickListener{
            addExercise()
        }

        binding.acceptButton.setOnClickListener {
            findNavController().navigate(R.id.action_createRoutineFragment_to_nav_routines)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun addExercise(){
        if(!(binding.routineExNameEditText.text.isEmpty() || binding.routineRepEditText.text.isEmpty() || binding.routineSeriesEditText.text.isEmpty())){
            val name = binding.routineExNameEditText.text.toString()
            val reps =  Integer.parseInt(binding.routineRepEditText.text.toString())
            val series = Integer.parseInt(binding.routineSeriesEditText.text.toString())
            val exercise = Exercise(name, series, reps)
            excercises.add(exercise)
            binding.listOfExcercisesTextView.setText(convertListIntoString(excercises))
            binding.routineExNameEditText.setText("")
            binding.routineRepEditText.setText("")
            binding.routineSeriesEditText.setText("")
        }
    }

    fun convertListIntoString(lista : MutableList<Exercise>) : String{
        var cadena = ""
        lista.forEach{
            cadena+="${it.name} - ${it.sets} series - ${it.reps} reps\n"
        }
        return cadena
    }

}