package com.nutrikares.nutrideskapp.ui.patients

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nutrikares.nutrideskapp.R
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.databinding.FragmentCreatePatientBinding
import com.nutrikares.nutrideskapp.databinding.FragmentCreateRoutineBinding
import java.lang.Exception


class CreatePatientFragment : Fragment() {
    private lateinit var database: DatabaseReference
    private var _binding: FragmentCreatePatientBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        database = Firebase.database.reference
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreatePatientBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.acceptButton.setOnClickListener {
            if(checkFields()){
                addPatient()
            }else{
                Toast.makeText(activity, "Faltan datos por llenar", Toast.LENGTH_LONG).show();
            }
        }

        binding.generateButton.setOnClickListener {
            binding.patientPasswordEditText.setText(generateRandomPassword())
        }
    }

    fun attachData(){
        Datasource.newUser.info.name= binding.patientNameEditText.text.toString()
        Datasource.newUser.info.age=Integer.parseInt(binding.patientAgeEditText.text.toString())
        Datasource.newUser.info.role = "patient"
        Datasource.newUser.info.objective = binding.patientObjectiveEditText.text.toString()
    }

    fun addPatient(){
        val  auth = Firebase.auth
        attachData()
        val email = binding.patientEmailEditText.text.toString()
        val password = binding.patientPasswordEditText.text.toString()
        activity?.let {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(it) { task ->
                    if (task.isSuccessful) {
                        var userId = task.result.user?.uid.toString()
                        auth.signOut()
                        Log.d("Firebase", "createUserWithEmail:success")
                        auth.signInWithEmailAndPassword("admin@example.com", "rootroot")
                            .addOnCompleteListener(requireActivity()) { task ->
                                if (task.isSuccessful) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success")
                                    addData(userId)
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(activity, "Error al crear nuevo paciente", Toast.LENGTH_LONG).show();
                                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                                }
                            }
                    } else {
                        Log.w("Firebase", "createUserWithEmail:failure")
                        Toast.makeText(activity, "Error al registrar las credenciales", Toast.LENGTH_LONG).show();
                    }
                }
        }
    }

    fun addData(userId : String){
        try{
            database.child("users").child(userId).setValue(Datasource.newUser.info)
            database.child("users_diets").child(userId).setValue(Datasource.newUser.diets)
            database.child("users_trainings").child(userId).setValue(Datasource.newUser.routines)
                .addOnCompleteListener {
                    Toast.makeText(activity, "Paciente registrado :)", Toast.LENGTH_LONG).show();
                    findNavController().navigate(R.id.action_createPatientFragment_to_nav_patients)
                }
        }catch(e : Exception){
            Log.d("Exception",e.toString())
            Toast.makeText(activity, "Error al cargar datos del paciente", Toast.LENGTH_LONG).show();
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun checkFields() : Boolean{
        return !(binding.patientNameEditText.text.isEmpty() || binding.patientAgeEditText.text.isEmpty()
                || binding.patientObjectiveEditText.text.isEmpty() || binding.patientEmailEditText.text.isEmpty()
                || binding.patientPasswordEditText.text.isEmpty())
    }

    fun generateRandomPassword(): String {
        val chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        var passWord = ""
        for (i in 0..31) {
            passWord += chars[Math.floor(Math.random() * chars.length).toInt()]
        }
        return passWord
    }

}