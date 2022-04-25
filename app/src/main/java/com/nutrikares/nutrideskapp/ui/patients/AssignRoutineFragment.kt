package com.nutrikares.nutrideskapp.ui.patients

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nutrikares.nutrideskapp.R
import com.nutrikares.nutrideskapp.adapters.AdapterListener
import com.nutrikares.nutrideskapp.adapters.AssignRoutineAdapter
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.databinding.FragmentAssignRoutineBinding

class AssignRoutineFragment : Fragment() {

    private var _binding: FragmentAssignRoutineBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAssignRoutineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.selectedRoutineTextView.text = resources.getString(R.string.selected_text, "")
        if(Datasource.routines.size==0){
            getRoutines()
        }else{
            // Recycler Viewer
            binding.routinesRecycler.adapter = AssignRoutineAdapter(Datasource.routines, object : AdapterListener {
                override fun onItemClick(p0: String) {
                    binding.selectedRoutineTextView.text = resources.getString(R.string.selected_text, p0)
                }
            })
            binding.routinesRecycler.setHasFixedSize(true)
        }

        binding.assignRoutineButton.setOnClickListener{
            findNavController().navigate(R.id.action_assignRoutineFragment_to_routineDaysFragment)
        }
        binding.noAssignRoutineButton.setOnClickListener{
            findNavController().navigate(R.id.action_assignRoutineFragment_to_routineDaysFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getRoutines(){
        val database : DatabaseReference = Firebase.database.reference

        database.child("trainings").get().addOnSuccessListener {

            Datasource.mapOfRoutines.clear()
            Datasource.routines.clear()

            for (ds in it.children) {
                Datasource.mapOfRoutines.put(ds.child("name").value.toString(),ds.key.toString())
                Datasource.routines.add(ds.child("name").value.toString())
            }

            // Recycler Viewer
            binding.routinesRecycler.adapter = AssignRoutineAdapter(Datasource.routines, object : AdapterListener{
                override fun onItemClick(p0: String) {
                    binding.selectedRoutineTextView.text = resources.getString(R.string.selected_text, p0)
                }
            })
            binding.routinesRecycler.setHasFixedSize(true)
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }
}