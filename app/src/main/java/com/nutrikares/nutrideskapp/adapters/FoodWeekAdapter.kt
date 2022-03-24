package com.nutrikares.nutrideskapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.nutrikares.nutrideskapp.FragmentFood
import com.nutrikares.nutrideskapp.FragmentFoodDirections
import com.nutrikares.nutrideskapp.R
import com.nutrikares.nutrideskapp.data.models.FoodDayMenu

class FoodWeekAdapter(
    private val context: FragmentFood?,
    private val days: List<FoodDayMenu>
): RecyclerView.Adapter<FoodWeekAdapter.FoodDayViewHolder>() {

    class FoodDayViewHolder(val view: View?): RecyclerView.ViewHolder(view!!) {
        // Declare and initialize all of the list item UI components
        val cardButton: MaterialCardView = view!!.findViewById(R.id.card_day)
        val dayTextView: TextView = view!!.findViewById(R.id.card_day_number)
        val dateTextView: TextView = view!!.findViewById(R.id.card_day_text)
        val previewImageSource: ImageView = view!!.findViewById(R.id.card_day_image)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) 0 else 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodDayViewHolder {

        // Inflate the layout
        val adapterLayout = when(viewType){
            0 -> LayoutInflater.from(parent.context).inflate(R.layout.food_days_list_items_bigger, parent, false)
            else -> LayoutInflater.from(parent.context).inflate(R.layout.food_days_list_items, parent, false)
        }

        return FoodDayViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int = days.size

    override fun onBindViewHolder(holder: FoodDayViewHolder, position: Int) {
        val day = days[position]
        val resources = context?.resources

        holder.dayTextView.text = resources?.getString(R.string.card_day, position.inc().toString())
        holder.dateTextView.text =  day.id
        //holder.previewImageSource.setImageResource(day.imageResourceId)

        // Assign onClickListener to each card
        holder.cardButton.setOnClickListener {
            val action = FragmentFoodDirections.actionFragmentFoodToFragmentFoodDaily(dayIndex = day.id)
            holder.view?.findNavController()!!.navigate(action)
        }
    }
}