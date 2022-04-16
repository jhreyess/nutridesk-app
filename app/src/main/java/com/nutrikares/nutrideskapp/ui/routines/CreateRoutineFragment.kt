package com.nutrikares.nutrideskapp.ui.routines

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nutrikares.nutrideskapp.R
import com.nutrikares.nutrideskapp.databinding.FragmentCreateRoutineBinding

class CreateRoutineFragment : Fragment() {

    private var _binding: FragmentCreateRoutineBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateRoutineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.acceptButton.setOnClickListener {
            findNavController().navigate(R.id.action_createRoutineFragment_to_nav_routines)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}