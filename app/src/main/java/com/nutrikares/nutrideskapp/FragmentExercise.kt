package com.nutrikares.nutrideskapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nutrikares.nutrideskapp.data.Datasource

class FragmentExercise : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        val layout = if(Datasource.getExercisesSize == 0) R.layout.fragment_exercise_empty else R.layout.fragment_exercise
        return inflater.inflate(layout, container, false)
    }

}