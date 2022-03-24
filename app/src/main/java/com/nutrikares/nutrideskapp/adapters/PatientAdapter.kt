package com.nutrikares.nutrideskapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.nutrikares.nutrideskapp.R
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.ui.patients.PatientsFragment

class PatientAdapter (private val context: PatientsFragment?,) : RecyclerView.Adapter<PatientAdapter.PatientViewHolder>() {

    private var patients = Datasource.patients

    class PatientViewHolder(val view: View?) : RecyclerView.ViewHolder(view!!) {
        val preview: TextView = view!!.findViewById(R.id.patient_preview)
        val cardButton: MaterialCardView = view!!.findViewById(R.id.card_patient)
        //val favorite = view.findViewById<ImageView>(R.id.favorite)
    }

    override fun getItemCount(): Int = patients.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.patients_list_items, parent, false)

        return PatientViewHolder(layout)
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        print(patients)
        val patient = patients[position]
        holder.preview.text = patient

        holder.cardButton.setOnClickListener{
            val action = R.id.action_nav_patients_to_viewPatientFragment2
            holder.view?.findNavController()!!.navigate(action)
        }
    }

}