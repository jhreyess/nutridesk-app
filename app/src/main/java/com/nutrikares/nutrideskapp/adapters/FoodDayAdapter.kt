package com.nutrikares.nutrideskapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.nutrikares.nutrideskapp.FragmentFoodDaily
import com.nutrikares.nutrideskapp.FragmentFoodDailyDirections
import com.nutrikares.nutrideskapp.R
import com.nutrikares.nutrideskapp.data.Datasource
import java.io.File

class FoodDayAdapter(
    private val context: FragmentFoodDaily?,
    private val dayIndex: String
): RecyclerView.Adapter<FoodDayAdapter.FoodDayViewHolder>() {

    private val data = Datasource.getUserDiets().days.values.toList().single { it.day == dayIndex }
    private val menu = data.foods.values.toList()


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

        holder.dishType.text = dish.type

        val imagePath = dish.imageResourceId
        val cacheFile = File(context?.requireActivity()?.cacheDir, imagePath)
        if(cacheFile.exists()){
            holder.previewImageSource.setImageURI(cacheFile.toUri())
        }else{
            Firebase.storage.reference.child("images").child(imagePath).getFile(cacheFile)
                .addOnCompleteListener {
                    if(it.isSuccessful) {
                        val uri = cacheFile.toUri()
                        holder.previewImageSource.setImageURI(uri)
                    }
                }
        }

        // Assign onClickListener to each card
        holder.cardButton.setOnClickListener {
            Datasource.setCurrentDay(dayIndex)
            val dishType = dish.getFoodType()
            val action = FragmentFoodDailyDirections.actionFragmentFoodDailyToFoodDetail(recipeType = dishType)
            holder.view?.findNavController()!!.navigate(action)
        }
    }
}