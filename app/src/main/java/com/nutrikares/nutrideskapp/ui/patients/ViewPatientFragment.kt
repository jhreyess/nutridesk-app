package com.nutrikares.nutrideskapp.ui.patients

import android.app.ProgressDialog
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nutrikares.nutrideskapp.R
import com.nutrikares.nutrideskapp.adapters.PatientAdapter
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.data.models.FoodWeekMenu
import com.nutrikares.nutrideskapp.data.models.Routine
import com.nutrikares.nutrideskapp.data.models.User
import com.nutrikares.nutrideskapp.data.models.UserInfo
import com.nutrikares.nutrideskapp.databinding.FragmentViewPatientBinding


class ViewPatientFragment : Fragment() {

    private var _binding: FragmentViewPatientBinding? = null
    private val binding get() = _binding!!

    companion object{
        var Nombre : String? = "Default"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewPatientBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val currentUserName = Datasource.getCurrentUser().info.name
        binding.patientNameTextView.text = currentUserName

        binding.editIcon.setOnClickListener {
            findNavController().navigate(R.id.action_viewPatientFragment2_to_editPatientFragment)
        }
        binding.anthropometryCard.setOnClickListener {
            findNavController().navigate(R.id.action_viewPatientFragment2_to_anthropometryPatientFragment)
        }
        binding.dietCard.setOnClickListener {
            findNavController().navigate(R.id.action_viewPatientFragment2_to_navigation_diet)
        }
        binding.excerciseCard.setOnClickListener {
            findNavController().navigate(R.id.action_viewPatientFragment2_to_navigation_exercises)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}