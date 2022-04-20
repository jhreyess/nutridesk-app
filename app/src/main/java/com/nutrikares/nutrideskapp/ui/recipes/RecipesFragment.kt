package com.nutrikares.nutrideskapp.ui.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nutrikares.nutrideskapp.R
import com.nutrikares.nutrideskapp.adapters.RecipeAdapter
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.databinding.FragmentRecipesBinding
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking


class RecipesFragment : Fragment() {
    lateinit var listOfRecipes : MutableList<String>
    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listOfRecipes = Datasource.getRecipes()

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recipesRecycler.adapter = RecipeAdapter(this,listOfRecipes)
        binding.recipesRecycler.setHasFixedSize(true)

        binding.createRecipeButton.setOnClickListener {
            Datasource.setClickOnRecipe(false)
            findNavController().navigate(R.id.action_nav_recipes_to_createRecipeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}