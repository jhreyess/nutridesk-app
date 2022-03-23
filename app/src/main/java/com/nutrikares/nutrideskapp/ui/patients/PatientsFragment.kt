package com.nutrikares.nutrideskapp.ui.patients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nutrikares.nutrideskapp.adapters.PatientAdapter
import com.nutrikares.nutrideskapp.databinding.FragmentPatientsBinding

class PatientsFragment : Fragment() {
    private var _binding: FragmentPatientsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
        // Recycler Viewer
        binding.patientsRecycler.adapter = PatientAdapter(this)
        binding.patientsRecycler.setHasFixedSize(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}