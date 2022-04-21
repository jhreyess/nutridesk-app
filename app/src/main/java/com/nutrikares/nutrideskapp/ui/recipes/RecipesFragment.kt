package com.nutrikares.nutrideskapp.ui.recipes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nutrikares.nutrideskapp.R
import com.nutrikares.nutrideskapp.adapters.RecipeAdapter
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.databinding.FragmentRecipesBinding
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking


class RecipesFragment : Fragment() {
    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getRecipes()
        binding.createRecipeButton.setOnClickListener {
            Datasource.setClickOnRecipe(false)
            findNavController().navigate(R.id.action_nav_recipes_to_createRecipeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getRecipes(){
        var database : DatabaseReference = Firebase.database.reference

        database.child("recipes").get().addOnSuccessListener {

            Datasource.mapOfRecipes.clear()
            Datasource.recipes.clear()

            for (ds in it.children) {
                Datasource.mapOfRecipes.put(ds.child("name").getValue().toString(),ds.key.toString())
                Datasource.recipes.add(ds.child("name").getValue().toString())
            }

            binding.recipesRecycler.adapter = RecipeAdapter(this,Datasource.recipes)
            binding.recipesRecycler.setHasFixedSize(true)

        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }
}