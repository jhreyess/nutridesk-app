package com.nutrikares.nutrideskapp.ui.patients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nutrikares.nutrideskapp.R
import com.nutrikares.nutrideskapp.adapters.AssignRecipeAdapter
import com.nutrikares.nutrideskapp.adapters.AssignRoutineAdapter
import com.nutrikares.nutrideskapp.databinding.FragmentAssignRoutineBinding
import com.nutrikares.nutrideskapp.databinding.FragmentAssingRecipeBinding

class AssignRoutineFragment : Fragment() {
    private var _binding: FragmentAssignRoutineBinding? = null
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

        _binding = FragmentAssignRoutineBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Recycler Viewer
        binding.routinesRecycler.adapter = AssignRoutineAdapter(this)
        binding.routinesRecycler.setHasFixedSize(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}