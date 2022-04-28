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
import com.nutrikares.nutrideskapp.FragmentFood
import com.nutrikares.nutrideskapp.FragmentFoodDirections
import com.nutrikares.nutrideskapp.R
import com.nutrikares.nutrideskapp.data.models.FoodDayMenu
import java.io.File

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
        val dayMenu = days[position]
        val resources = context?.resources

        holder.dayTextView.text = resources?.getString(R.string.card_day, position.inc().toString())
        holder.dateTextView.text =  dayMenu.day

        val imagePath = dayMenu.imageUri
        if(imagePath.isNotBlank()){
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
        }

        if(dayMenu.foods.isNotEmpty()){
            // Assign onClickListener to each card
            holder.cardButton.setOnClickListener {
                val action = FragmentFoodDirections.actionFragmentFoodToFragmentFoodDaily(dayIndex = dayMenu.day)
                holder.view?.findNavController()!!.navigate(action)
            }
        }
    }
}