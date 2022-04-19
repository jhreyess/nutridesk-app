package com.nutrikares.nutrideskapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nutrikares.nutrideskapp.R
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.ui.patients.PatientsFragment
import com.nutrikares.nutrideskapp.ui.recipes.RecipesFragment

class RecipeAdapter(private val context: RecipesFragment?,) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    private var recipes = Datasource.recipes

    class RecipeViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        val preview: TextView = view!!.findViewById(R.id.recipe_preview)
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
    }

}