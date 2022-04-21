package com.nutrikares.nutrideskapp.ui.patients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nutrikares.nutrideskapp.R
import com.nutrikares.nutrideskapp.databinding.FragmentViewPatientBinding


class ViewPatientFragment : Fragment() {

    private var _binding: FragmentViewPatientBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
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

    //val args: ViewPatientFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val patientName = Nombre
        binding.patientNameTextView.text = patientName

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