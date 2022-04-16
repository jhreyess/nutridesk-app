package com.nutrikares.nutrideskapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.nutrikares.nutrideskapp.FragmentFoodDaily
import com.nutrikares.nutrideskapp.R
import com.nutrikares.nutrideskapp.data.Datasource

class FoodDayAdapter(
    private val context: FragmentFoodDaily?,
    dayIndex: String
): RecyclerView.Adapter<FoodDayAdapter.FoodDayViewHolder>() {

    private val menu = Datasource.weekMenu.single{ it.id == dayIndex }.foods

    class FoodDayViewHolder(val view: View?): RecyclerView.ViewHolder(view!!) {
        // Declare and initialize all of the list item UI components
        val cardButton: MaterialCardView = view!!.findViewById(R.id.dish_card)
        val dishType: TextView = view!!.findViewById(R.id.dish_type)
        val previewImageSource: ImageView = view!!.findViewById(R.id.dish_preview)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) 0 else 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodDayViewHolder {

        // Inflate the layout
        val adapterLayout = when(viewType){
            0 -> LayoutInflater.from(parent.context).inflate(R.layout.food_daily_list_items_bigger, parent, false)
            else -> LayoutInflater.from(parent.context).inflate(R.layout.food_daily_list_items, parent, false)
        }

        return FoodDayViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int = menu.size

    override fun onBindViewHolder(holder: FoodDayViewHolder, position: Int) {
        val dish = menu[position]
        val resources = context?.resources

        holder.dishType.text = dish.type
        //holder.previewImageSource.setImageResource(menu.imageResourceId)

        // Assign onClickListener to each card
        holder.cardButton.setOnClickListener {
            //val action = FoodDayFragmentDirections.actionHomeFragmentToQuotesListFragment(foods = menu.foods)
            //holder.view?.findNavController()!!.navigate(action)
        }
    }
}