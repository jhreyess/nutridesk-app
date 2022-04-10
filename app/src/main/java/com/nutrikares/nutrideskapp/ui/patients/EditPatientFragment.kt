package com.nutrikares.nutrideskapp.ui.patients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nutrikares.nutrideskapp.R
import com.nutrikares.nutrideskapp.databinding.FragmentEditPatientBinding
import com.nutrikares.nutrideskapp.databinding.FragmentViewPatientBinding


class EditPatientFragment : Fragment() {

    private var _binding: FragmentEditPatientBinding? = null
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
        _binding = FragmentEditPatientBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.acceptButtonEdit.setOnClickListener {
            findNavController().navigate(R.id.action_editPatientFragment_to_viewPatientFragment2)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}