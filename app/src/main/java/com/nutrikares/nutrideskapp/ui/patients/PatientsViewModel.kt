package com.nutrikares.nutrideskapp.ui.patients

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PatientsViewModel : ViewModel() {
    val patientName: MutableLiveData<String> = MutableLiveData(" ")

    fun setPatientName(value : String){
        patientName.value = value
    }
}