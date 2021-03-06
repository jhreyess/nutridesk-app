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
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nutrikares.nutrideskapp.R
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.data.models.FoodWeekMenu
import com.nutrikares.nutrideskapp.data.models.Routine
import com.nutrikares.nutrideskapp.data.models.User
import com.nutrikares.nutrideskapp.data.models.UserInfo
import com.nutrikares.nutrideskapp.ui.patients.PatientsFragment
import com.nutrikares.nutrideskapp.ui.patients.PatientsFragmentDirections
import com.nutrikares.nutrideskapp.ui.patients.ViewPatientFragment

class PatientAdapter(
    private val context: PatientsFragment?,
    private var patients : List<String>) : RecyclerView.Adapter<PatientAdapter.PatientViewHolder>() {

    class PatientViewHolder(val view: View?) : RecyclerView.ViewHolder(view!!) {
        val preview: TextView = view!!.findViewById(R.id.patient_preview)
        val cardButton: MaterialCardView = view!!.findViewById(R.id.card_patient)
    }

    override fun getItemCount(): Int = patients.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.patients_list_items, parent, false)
        return PatientViewHolder(layout)
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        val patient = patients[position]
        holder.preview.text = patient

        holder.cardButton.setOnClickListener{
            ViewPatientFragment.Nombre = patient
            queryUser(holder, Datasource.mapOfPatients[patient].toString())
        }
    }

    fun queryUser(holder: PatientAdapter.PatientViewHolder, key:String){
        var database : DatabaseReference = Firebase.database.reference
        database.child("users").child(key).get().addOnSuccessListener { snapshot ->
            val userInfo = snapshot.getValue(UserInfo::class.java)!!
            database.child("users_diets").child(key).get().addOnSuccessListener {
                val userDiets = it.getValue(FoodWeekMenu::class.java)!!
                database.child("users_trainings").child(key).get().addOnSuccessListener { dataSnapshot ->
                    val x = object : GenericTypeIndicator<MutableMap<String, Routine>>(){}
                    val userTrainings: MutableMap<String, Routine>? = dataSnapshot.getValue(x)
                    val user : User = User()
                    user.info = userInfo
                    user.diets = userDiets
                    if (userTrainings != null) {
                        user.routines = userTrainings
                    }
                    Datasource.setCurrentUser(user)
                    val action = PatientsFragmentDirections.actionNavPatientsToNavigationViewPatient()
                    holder.view?.findNavController()!!.navigate(action)
                }.addOnFailureListener{
                    Log.e("firebase", "Error getting data", it)
                }
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }

}