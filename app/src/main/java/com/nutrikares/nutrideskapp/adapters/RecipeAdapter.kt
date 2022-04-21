package com.nutrikares.nutrideskapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nutrikares.nutrideskapp.R
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.data.models.Food
import com.nutrikares.nutrideskapp.ui.recipes.RecipesFragment
import kotlinx.coroutines.CoroutineScope

class RecipeAdapter(private val context: RecipesFragment, private var recipes:MutableList<String>) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    class RecipeViewHolder(val view: View?) : RecyclerView.ViewHolder(view!!) {
        val preview: TextView = view!!.findViewById(R.id.recipe_preview)
        val cardButton: MaterialCardView = view!!.findViewById(R.id.card_recipe)
    }

    override fun getItemCount(): Int = recipes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeAdapter.RecipeViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipes_list_items, parent, false)

        return RecipeAdapter.RecipeViewHolder(layout)
    }

    override fun onBindViewHolder(holder: RecipeAdapter.RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.preview.text = recipe

        holder.cardButton.setOnClickListener {
            queryRecipe(holder, Datasource.mapOfRecipes[recipe].toString())
        }
    }


    fun queryRecipe(holder: RecipeAdapter.RecipeViewHolder, key:String){
        var database : DatabaseReference = Firebase.database.reference

        database.child("recipes").child(key).get().addOnSuccessListener {
            Datasource.setCurrentRecipe(it.getValue(Food::class.java)!!)
            Datasource.setClickOnRecipe(true)
            holder.view?.findNavController()?.navigate(R.id.action_nav_recipes_to_createRecipeFragment)
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }

}