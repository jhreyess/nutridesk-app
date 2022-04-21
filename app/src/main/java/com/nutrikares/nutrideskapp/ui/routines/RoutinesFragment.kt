package com.nutrikares.nutrideskapp.ui.routines

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
import com.nutrikares.nutrideskapp.adapters.RecipeAdapter
import com.nutrikares.nutrideskapp.adapters.RoutineAdapter
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.databinding.FragmentRoutinesBinding


class RoutinesFragment : Fragment() {

    private var _binding: FragmentRoutinesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoutinesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getRoutines()

        binding.createRoutineButton.setOnClickListener {
            findNavController().navigate(R.id.action_nav_routines_to_createRoutineFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getRoutines(){
        var database : DatabaseReference = Firebase.database.reference

        database.child("trainings").get().addOnSuccessListener {

            Datasource.mapOfRoutines.clear()
            Datasource.routines.clear()

            for (ds in it.children) {
                Datasource.mapOfRoutines.put(ds.child("name").getValue().toString(),ds.key.toString())
                Datasource.routines.add(ds.child("name").getValue().toString())
            }

            // Recycler Viewer
            binding.routinesRecycler.adapter = RoutineAdapter(this,Datasource.routines)
            binding.routinesRecycler.setHasFixedSize(true)

        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }
}