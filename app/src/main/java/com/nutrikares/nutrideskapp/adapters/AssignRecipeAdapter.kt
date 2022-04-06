package com.nutrikares.nutrideskapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nutrikares.nutrideskapp.R
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.ui.patients.AssingRecipeFragment

class AssignRecipeAdapter (private val context: AssingRecipeFragment?,) : RecyclerView.Adapter<AssignRecipeAdapter.RecipeViewHolder>() {

    private var recipes = Datasource.recipes

    class RecipeViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        val preview: TextView = view!!.findViewById(R.id.recipe_preview)
    }

    override fun getItemCount(): Int = recipes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssignRecipeAdapter.RecipeViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipes_list_items, parent, false)

        return AssignRecipeAdapter.RecipeViewHolder(layout)
    }

    override fun onBindViewHolder(holder: AssignRecipeAdapter.RecipeViewHolder, position: Int) {
        print(recipes)
        val recipe = recipes[position]
        holder.preview.text = recipe
    }
}