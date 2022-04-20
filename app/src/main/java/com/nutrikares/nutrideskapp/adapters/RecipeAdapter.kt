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
import com.nutrikares.nutrideskapp.R
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.data.models.Food
import com.nutrikares.nutrideskapp.ui.recipes.RecipesFragment
import kotlinx.coroutines.CoroutineScope

class RecipeAdapter(private val context: RecipesFragment, private var recipes:MutableList<String>) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    //private var recipes = Datasource.recipes

    class RecipeViewHolder(val view: View?) : RecyclerView.ViewHolder(view!!) {
        val preview: TextView = view!!.findViewById(R.id.recipe_preview)
        val cardButton: MaterialCardView = view!!.findViewById(R.id.card_recipe)
        //val favorite = view.findViewById<ImageView>(R.id.favorite)
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
            //Log.v("Firebase",Datasource.mapOfRecipes[recipe].toString())
            //Log.v("Firebase-recipe",Datasource.getCurrentRecipe().toString())
            Datasource.queryRecipe(Datasource.mapOfRecipes[recipe].toString())
            Datasource.setClickOnRecipe(true)
            holder.view?.findNavController()?.navigate(R.id.action_nav_recipes_to_createRecipeFragment)
        }
    }

}