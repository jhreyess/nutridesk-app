package com.nutrikares.nutrideskapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nutrikares.nutrideskapp.R
import com.nutrikares.nutrideskapp.data.models.Exercise
import com.nutrikares.nutrideskapp.ui.routines.RoutinesFragment


class ExercisesAdapter(
    private val context: Context,
    private val data: List<Exercise>
    ) : RecyclerView.Adapter<ExercisesAdapter.ExerciseViewHolder>() {

    class ExerciseViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        val exerciseName: TextView = view!!.findViewById(R.id.exercise_name)
        val exerciseSetsReps: TextView = view!!.findViewById(R.id.exercise_sets_reps)
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.exercise_list_items, parent, false)

        return ExerciseViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val item = data[position]
        holder.exerciseName.text = item.name
        holder.exerciseSetsReps.text = context.resources.getString(R.string.sets_reps,
            item.sets.toString(), item.reps.toString())
    }

}