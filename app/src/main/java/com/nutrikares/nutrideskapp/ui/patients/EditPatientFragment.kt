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
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.data.models.UserInfo
import com.nutrikares.nutrideskapp.databinding.FragmentEditPatientBinding
import java.lang.Exception


class EditPatientFragment : Fragment() {

    private var _binding: FragmentEditPatientBinding? = null
    private val binding get() = _binding!!
    private lateinit var database : DatabaseReference
    lateinit var userInfo: UserInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Firebase.database.reference
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditPatientBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.acceptButtonEdit.isFocusable = true
        userInfo = Datasource.getCurrentUser().info
        binding.editPatientNameEditText.setText(userInfo.name)
        binding.editPatientAgeEditText.setText(userInfo.age.toString())
        binding.editPatientObjectiveEditText.setText(userInfo.objective)


        binding.acceptButtonEdit.setOnClickListener {
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
        return !(binding.editPatientAgeEditText.text.isEmpty() ||
                binding.editPatientNameEditText.text.isEmpty() ||
                binding.editPatientObjectiveEditText.text.isEmpty())
    }

    fun attachData(){
        userInfo.name = binding.editPatientNameEditText.text.toString()
        userInfo.age = Integer.parseInt(binding.editPatientAgeEditText.text.toString())
        userInfo.objective = binding.editPatientObjectiveEditText.text.toString()
    }

    fun uploadPatientData(){
        attachData()
        try{
            Datasource.mapOfPatients[Datasource.getCurrentUser().info.name]?.let {
                database.child("users").child(it).setValue(userInfo)
                    .addOnSuccessListener {
                        Toast.makeText(activity, "Datos modificados exitosamente", Toast.LENGTH_LONG).show();
                        findNavController().navigate(R.id.action_editPatientFragment_to_viewPatientFragment2)
                    }
            }
        }catch (e : Exception){
            Log.d("Exception",e.toString())
            Toast.makeText(activity, "Los datos no pudieron ser modificados", Toast.LENGTH_LONG).show();
        }
    }

}