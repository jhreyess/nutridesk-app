package com.nutrikares.nutrideskapp.ui.patients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nutrikares.nutrideskapp.R
import com.nutrikares.nutrideskapp.adapters.AssignRecipeAdapter
import com.nutrikares.nutrideskapp.adapters.RecipeAdapter
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.databinding.FragmentAssingRecipeBinding
import com.nutrikares.nutrideskapp.databinding.FragmentRecipesBinding


class AssingRecipeFragment : Fragment() {
    private var _binding: FragmentAssingRecipeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAssingRecipeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Recycler Viewer
        binding.recipesRecycler.adapter = AssignRecipeAdapter(this,Datasource.recipes)
        binding.recipesRecycler.setHasFixedSize(true)
        binding.assignButton.setOnClickListener{
            findNavController().navigate(R.id.action_assingRecipeFragment_to_dayRecipeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}