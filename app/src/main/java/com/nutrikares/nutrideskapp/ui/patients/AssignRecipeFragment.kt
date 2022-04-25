package com.nutrikares.nutrideskapp.ui.patients

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
import com.nutrikares.nutrideskapp.adapters.AdapterListener
import com.nutrikares.nutrideskapp.adapters.AssignRecipeAdapter
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.databinding.FragmentAssignRecipeBinding


class AssignRecipeFragment : Fragment() {
    private var _binding: FragmentAssignRecipeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAssignRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.selectedRecipeTextView.text = resources.getString(R.string.selected_text, "")
        if(Datasource.recipes.size==0){
            getRecipes()
        }else{
            binding.recipesRecycler.adapter = AssignRecipeAdapter(Datasource.recipes, object : AdapterListener {
                override fun onItemClick(p0: String) {
                    binding.selectedRecipeTextView.text = resources.getString(R.string.selected_text, p0)
                }
            })
            binding.recipesRecycler.setHasFixedSize(true)
        }

        binding.assignButton.setOnClickListener{
            findNavController().navigate(R.id.action_assignRecipeFragment_to_dayRecipeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getRecipes(){
        Log.d("Recipes","Entró")
        val database : DatabaseReference = Firebase.database.reference

        database.child("recipes").get().addOnSuccessListener {

            Datasource.mapOfRecipes.clear()
            Datasource.recipes.clear()

            for (ds in it.children) {
                Datasource.mapOfRecipes.put(ds.child("name").value.toString(),ds.key.toString())
                Datasource.recipes.add(ds.child("name").value.toString())
            }
            // Recycler Viewer
            binding.recipesRecycler.adapter = AssignRecipeAdapter(Datasource.recipes, object : AdapterListener {
                override fun onItemClick(p0: String) {
                    binding.selectedRecipeTextView.text = resources.getString(R.string.selected_text, p0)
                }
            })
            binding.recipesRecycler.setHasFixedSize(true)

        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }
}