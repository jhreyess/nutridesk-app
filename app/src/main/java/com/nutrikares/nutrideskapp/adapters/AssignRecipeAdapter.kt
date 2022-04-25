package com.nutrikares.nutrideskapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.nutrikares.nutrideskapp.R

class AssignRecipeAdapter (
    private var recipes: MutableList<String>,
    private var callback: AdapterListener
    ) : RecyclerView.Adapter<AssignRecipeAdapter.RecipeViewHolder>() {

    class RecipeViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        val preview: TextView = view!!.findViewById(R.id.recipe_preview)
        val cardButton: MaterialCardView = view!!.findViewById(R.id.card_recipe)
    }

    override fun getItemCount(): Int = recipes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipes_list_items, parent, false)

        return RecipeViewHolder(layout)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        print(recipes)
        val recipe = recipes[position]
        holder.preview.text = recipe
        holder.cardButton.setOnClickListener{
            callback.onItemClick(recipe)
        }
    }
}