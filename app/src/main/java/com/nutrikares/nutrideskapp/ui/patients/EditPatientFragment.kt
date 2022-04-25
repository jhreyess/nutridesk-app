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
import com.nutrikares.nutrideskapp.data.models.User
import com.nutrikares.nutrideskapp.data.models.UserInfo
import com.nutrikares.nutrideskapp.databinding.FragmentEditPatientBinding
import java.lang.Exception


class EditPatientFragment : Fragment() {

    private var _binding: FragmentEditPatientBinding? = null
    private val binding get() = _binding!!
    private lateinit var database : DatabaseReference
    lateinit var user:User
    lateinit var userInfo : UserInfo
    lateinit var userName : String

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
        user = Datasource.getCurrentUser()
        userInfo = user.info
        binding.editPatientNameEditText.setText(userInfo.name)
        binding.editPatientAgeEditText.setText(userInfo.age.toString())
        Log.d("PatientEdit - getData",userInfo.toString())
        userName = userInfo.name

        binding.acceptButtonEdit.setOnClickListener {
            if(checkFields()){
                Log.d("PatientEdit - intoCheckFields",userInfo.toString())
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
                binding.editPatientNameEditText.text.isEmpty())
    }

    fun attachData(){
        userInfo.name = binding.editPatientNameEditText.text.toString()
        userInfo.age = Integer.parseInt(binding.editPatientAgeEditText.text.toString())
        user.info = userInfo
    }

    fun uploadPatientData(){
        attachData()
        val id = Datasource.mapOfPatients[userName].toString()
        try{
            database.child("users").child(id).setValue(userInfo)
                .addOnSuccessListener {
                    Datasource.setCurrentUser(user)
                    Toast.makeText(activity, "Datos modificados exitosamente", Toast.LENGTH_LONG).show();
                    findNavController().navigate(R.id.action_editPatientFragment_to_viewPatientFragment2)
                }
        }catch (e : Exception){
            Log.d("Exception",e.toString())
            Toast.makeText(activity, "Los datos no pudieron ser modificados", Toast.LENGTH_LONG).show();
        }
    }

}