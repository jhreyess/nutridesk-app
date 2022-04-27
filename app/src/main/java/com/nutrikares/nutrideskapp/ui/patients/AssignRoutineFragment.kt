package com.nutrikares.nutrideskapp.ui.patients

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nutrikares.nutrideskapp.R
import com.nutrikares.nutrideskapp.adapters.AdapterListener
import com.nutrikares.nutrideskapp.adapters.AssignRoutineAdapter
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.data.models.Routine
import com.nutrikares.nutrideskapp.data.models.User
import com.nutrikares.nutrideskapp.databinding.FragmentAssignRoutineBinding
import java.lang.Exception

class AssignRoutineFragment : Fragment() {

    private var _binding: FragmentAssignRoutineBinding? = null
    private val binding get() = _binding!!
    private var selectedRoutine : String = ""
    private lateinit var database : DatabaseReference
    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Firebase.database.reference
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAssignRoutineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        user = Datasource.getCurrentUser()
        if (user.routines[Datasource.routineDaySelected]?.name.equals("")){
            binding.selectedRoutineTextView.text = resources.getString(R.string.selected_text, "")
        }else{
            val routineName = user.routines[Datasource.routineDaySelected]?.name
            selectedRoutine = routineName.toString()
            binding.selectedRoutineTextView.text = resources.getString(R.string.selected_text,routineName)
        }
        if(Datasource.routines.size==0){
            getRoutines()
        }else{
            binding.routinesRecycler.adapter = AssignRoutineAdapter(Datasource.routines, object : AdapterListener {
                override fun onItemClick(p0: String) {
                    binding.selectedRoutineTextView.text = resources.getString(R.string.selected_text, p0)
                    selectedRoutine = p0
                }
            })
            binding.routinesRecycler.setHasFixedSize(true)
        }
        binding.searchRoutineIcon.setOnClickListener{
            searchRoutines(binding.searchRoutineEditText.text.toString())
        }

        binding.assignRoutineButton.setOnClickListener{
            if(selectedRoutine != ""){
                setRoutine()
            }else{
                Toast.makeText(activity, "No hay una rutina seleccionada", Toast.LENGTH_LONG).show();
            }
        }
        binding.noAssignRoutineButton.setOnClickListener{
            setVoidRoutine()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun searchRoutines(text : String){
        if(text.isEmpty()){
            binding.routinesRecycler.adapter = AssignRoutineAdapter(Datasource.routines, object : AdapterListener {
                override fun onItemClick(p0: String) {
                    selectedRoutine = p0
                    binding.selectedRoutineTextView.text = resources.getString(R.string.selected_text, p0)
                }
            })
            binding.routinesRecycler.setHasFixedSize(true)
        }else{
            val newList = mutableListOf<String>()
            for(routine in Datasource.routines){
                if(routine.contains(text, true)){
                    newList.add(routine)
                }
            }
            binding.routinesRecycler.adapter = AssignRoutineAdapter(newList, object : AdapterListener {
                override fun onItemClick(p0: String) {
                    selectedRoutine = p0
                    binding.selectedRoutineTextView.text = resources.getString(R.string.selected_text, p0)
                }
            })
            binding.routinesRecycler.setHasFixedSize(true)
        }
    }

    private fun getRoutines(){
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
                    selectedRoutine = p0
                    binding.selectedRoutineTextView.text = resources.getString(R.string.selected_text, p0)
                }
            })
            binding.routinesRecycler.setHasFixedSize(true)
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }

    private fun setRoutine(){
        val key = Datasource.mapOfRoutines[selectedRoutine].toString()
        database.child("trainings").child(key).get().addOnSuccessListener {
            val routine = it.getValue(Routine::class.java)!!
            val id = Datasource.mapOfPatients[Datasource.getCurrentUser().info.name].toString()
            val day = Datasource.routineDaySelected
            user.routines[day] = routine
            try{
                routine.hasRoutines = true
                database.child("users_trainings").child(id).child(day).setValue(routine)
                    .addOnSuccessListener {
                        Datasource.setCurrentUser(user)
                        Toast.makeText(activity, "Rutina adjuntada correctamente", Toast.LENGTH_LONG).show();
                        findNavController().navigate(R.id.action_assignRoutineFragment_to_routineDaysFragment)
                    }
            }catch (e : Exception){
                Log.d("Exception",e.toString())
                Toast.makeText(activity, "Rutina no pudo ser adjuntada", Toast.LENGTH_LONG).show();
            }
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }

    private fun setVoidRoutine(){
        val voidRoutine = Routine()
        val id = Datasource.mapOfPatients[Datasource.getCurrentUser().info.name].toString()
        val day = Datasource.routineDaySelected
        user.routines[day] = voidRoutine
        try{
            database.child("users_trainings").child(id).child(day).setValue(voidRoutine)
                .addOnSuccessListener {
                    Datasource.setCurrentUser(user)
                    Toast.makeText(activity, "Rutina no asignada", Toast.LENGTH_LONG).show();
                    findNavController().navigate(R.id.action_assignRoutineFragment_to_routineDaysFragment)
                }
        }catch (e : Exception){
            Log.d("Exception",e.toString())
            Toast.makeText(activity, "Error al no adjuntar rutina", Toast.LENGTH_LONG).show();
        }
    }
}