package com.nutrikares.nutrideskapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nutrikares.nutrideskapp.R
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.ui.patients.AssignRoutineFragment

class AssignRoutineAdapter(private val context: AssignRoutineFragment?,) : RecyclerView.Adapter<AssignRoutineAdapter.RoutineViewHolder>() {

    private var routines = Datasource.routines

    class RoutineViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        val preview: TextView = view!!.findViewById(R.id.routine_preview)
        //val favorite = view.findViewById<ImageView>(R.id.favorite)
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
    }

}