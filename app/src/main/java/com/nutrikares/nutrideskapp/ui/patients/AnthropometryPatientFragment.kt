package com.nutrikares.nutrideskapp.ui.patients

import android.os.Bundle
import android.provider.ContactsContract
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
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.data.models.Stats
import com.nutrikares.nutrideskapp.data.models.User
import com.nutrikares.nutrideskapp.databinding.FragmentAnthropometryPatientBinding
import java.lang.Exception

class AnthropometryPatientFragment : Fragment() {

    private var _binding: FragmentAnthropometryPatientBinding? = null
    private val binding get() = _binding!!
    private lateinit var database : DatabaseReference
    lateinit var user: User
    lateinit var userInfoStats: Stats
    lateinit var userName : String

    override fun onCreate(savedInstanceState: Bundle?) {
        database = Firebase.database.reference
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnthropometryPatientBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        userInfoStats = Datasource.getCurrentUser().info.stats
        userName = Datasource.getCurrentUser().info.name
        user= Datasource.getCurrentUser()

        binding.absStatPatientEditText.setText(userInfoStats.abs.toString())
        binding.hipStatPatientEditText.setText(userInfoStats.hip.toString())
        binding.imcStatPatientEditText.setText(userInfoStats.imc.toString())
        val lastRelativeIndex = userInfoStats.relative.elementAt(4).toDouble()
        binding.relativeStatPatientEditText.setText(lastRelativeIndex.toString())
        binding.waistStatPatientEditText.setText(userInfoStats.waist.toString())
        val lastWeightIndex = userInfoStats.progress.elementAt(4).toDouble()
        binding.weightStatPatientEditText.setText(lastWeightIndex.toString())

        binding.updateMeasuresButton.setOnClickListener{
            if(checkFields()){
                uploadPatientData()
            }else{
                Toast.makeText(activity, "Faltan datos por llenar", Toast.LENGTH_LONG).show();
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun checkFields():Boolean{
        return !(binding.absStatPatientEditText.text.isEmpty() ||
                binding.hipStatPatientEditText.text.isEmpty() ||
                binding.imcStatPatientEditText.text.isEmpty() ||
                binding.relativeStatPatientEditText.text.isEmpty() ||
                binding.waistStatPatientEditText.text.isEmpty() ||
                binding.weightStatPatientEditText.text.isEmpty())
    }

    fun attachData(){
        userInfoStats.abs = binding.absStatPatientEditText.text.toString().toDouble()
        userInfoStats.hip = binding.hipStatPatientEditText.text.toString().toDouble()
        userInfoStats.imc =  binding.imcStatPatientEditText.text.toString().toDouble()
        val newRelative = binding.relativeStatPatientEditText.text.toString().toDouble()
        userInfoStats.relative.removeAt(0)
        userInfoStats.relative.add(newRelative)
        userInfoStats.waist =  binding.waistStatPatientEditText.text.toString().toDouble()
        userInfoStats.weight =  binding.weightStatPatientEditText.text.toString().toDouble()
        userInfoStats.progress.removeAt(0)
        userInfoStats.progress.add(userInfoStats.weight)
        user.info.stats = userInfoStats
    }

    fun uploadPatientData(){
        attachData()
        val id = Datasource.mapOfPatients[userName].toString()
        try{
            database.child("users").child(id).child("stats").setValue(userInfoStats)
                .addOnSuccessListener {
                    Datasource.setCurrentUser(user)
                    Toast.makeText(activity, "Datos modificados exitosamente", Toast.LENGTH_LONG).show();
                    findNavController().navigate(R.id.action_anthropometryPatientFragment_to_viewPatientFragment2)
                }
        }catch (e : Exception){
            Log.d("Exception",e.toString())
            Toast.makeText(activity, "Los datos no pudieron ser modificados", Toast.LENGTH_LONG).show();
        }
    }

}