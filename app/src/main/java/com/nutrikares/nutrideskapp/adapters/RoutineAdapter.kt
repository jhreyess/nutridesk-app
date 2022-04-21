package com.nutrikares.nutrideskapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nutrikares.nutrideskapp.R
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.data.models.Food
import com.nutrikares.nutrideskapp.data.models.Routine
import com.nutrikares.nutrideskapp.ui.patients.PatientsFragment
import com.nutrikares.nutrideskapp.ui.routines.RoutinesFragment

class RoutineAdapter (private val context: RoutinesFragment?,private var routines:MutableList<String>) : RecyclerView.Adapter<RoutineAdapter.RoutineViewHolder>() {

    class RoutineViewHolder(var view: View?) : RecyclerView.ViewHolder(view!!) {
        val preview: TextView = view!!.findViewById(R.id.routine_preview)
        val cardButton: MaterialCardView = view!!.findViewById(R.id.card_routine)
    }

    override fun getItemCount(): Int = routines.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineAdapter.RoutineViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.routines_list_items, parent, false)

        return RoutineAdapter.RoutineViewHolder(layout)
    }

    override fun onBindViewHolder(holder: RoutineAdapter.RoutineViewHolder, position: Int) {
        val routine = routines[position]
        holder.preview.text = routine

        holder.cardButton.setOnClickListener {
            queryRoutine(holder, Datasource.mapOfRoutines[routine].toString())
        }
    }

    fun queryRoutine(holder: RoutineAdapter.RoutineViewHolder, key:String){
        var database : DatabaseReference = Firebase.database.reference

        database.child("trainings").child(key).get().addOnSuccessListener {
            Datasource.setCurrentRoutine(it.getValue(Routine::class.java)!!)
            Datasource.setClickOnRoutine(true)
            holder.view?.findNavController()?.navigate(R.id.action_nav_routines_to_createRoutineFragment)
            Log.v("firebase", Datasource.getCurrentRoutine().toString())
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }

}