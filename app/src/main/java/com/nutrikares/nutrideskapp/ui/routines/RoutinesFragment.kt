package com.nutrikares.nutrideskapp.ui.routines

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.nutrikares.nutrideskapp.R
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

        val textView: TextView = binding.textRoutines

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //_binding = null
    }
}