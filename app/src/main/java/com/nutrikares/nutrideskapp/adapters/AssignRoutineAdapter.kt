package com.nutrikares.nutrideskapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.nutrikares.nutrideskapp.R

interface AdapterListener {
    fun onItemClick(p0: String)
}

class AssignRoutineAdapter(
    private var routines:MutableList<String>,
    private var callback: AdapterListener
    ) : RecyclerView.Adapter<AssignRoutineAdapter.RoutineViewHolder>() {


    class RoutineViewHolder(var view: View?) : RecyclerView.ViewHolder(view!!) {
        val preview: TextView = view!!.findViewById(R.id.routine_preview)
        val cardButton: MaterialCardView = view!!.findViewById(R.id.card_routine)
    }

    override fun getItemCount(): Int = routines.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.routines_list_items, parent, false)

        return RoutineViewHolder(layout)
    }

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        print(routines)
        val routine = routines[position]
        holder.preview.text = routine
        holder.cardButton.setOnClickListener {
            callback.onItemClick(routine)
        }
    }
}
