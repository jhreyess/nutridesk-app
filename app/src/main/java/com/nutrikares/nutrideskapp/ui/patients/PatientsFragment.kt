package com.nutrikares.nutrideskapp.ui.patients

import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nutrikares.nutrideskapp.R
import com.nutrikares.nutrideskapp.adapters.PatientAdapter
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.databinding.FragmentPatientsBinding
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.coroutineContext

class PatientsFragment : Fragment() {
    private var _binding: FragmentPatientsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPatientsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getPatients()

        binding.createPatientButton.setOnClickListener {
            findNavController().navigate(R.id.action_nav_patients_to_createPatientFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getPatients(){
        var database : DatabaseReference = Firebase.database.reference

        database.child("users").get().addOnSuccessListener {

            Datasource.mapOfPatients.clear()
            Datasource.patients.clear()

            for (ds in it.children) {
                if(ds.child("role").getValue().toString().equals("patient")){
                    Datasource.mapOfPatients.put(ds.child("name").getValue().toString(),ds.key.toString())
                    Datasource.patients.add(ds.child("name").getValue().toString())
                }
            }

            // Recycler Viewer
            binding.patientsRecycler.adapter = PatientAdapter(this,Datasource.patients)
            binding.patientsRecycler.setHasFixedSize(true)

        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }

}