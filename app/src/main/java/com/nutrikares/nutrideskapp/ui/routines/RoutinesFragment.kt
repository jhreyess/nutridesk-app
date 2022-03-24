package com.nutrikares.nutrideskapp.ui.routines

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavAction
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.nutrikares.nutrideskapp.R
import com.nutrikares.nutrideskapp.adapters.PatientAdapter
import com.nutrikares.nutrideskapp.adapters.RoutineAdapter
import com.nutrikares.nutrideskapp.databinding.FragmentRecipesBinding
import com.nutrikares.nutrideskapp.databinding.FragmentRoutinesBinding


class RoutinesFragment : Fragment() {
    private var _binding: FragmentRoutinesBinding? = null
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
        _binding = FragmentRoutinesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Recycler Viewer
        binding.routinesRecycler.adapter = RoutineAdapter(this)
        binding.routinesRecycler.setHasFixedSize(true)

        binding.createRoutineButton.setOnClickListener {
            findNavController().navigate(R.id.action_nav_routines_to_createRoutineFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}