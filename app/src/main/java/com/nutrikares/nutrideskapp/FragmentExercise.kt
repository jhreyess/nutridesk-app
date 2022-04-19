package com.nutrikares.nutrideskapp

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.nutrikares.nutrideskapp.adapters.ExercisesAdapter
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.data.models.Routine
import java.io.File

class FragmentExercise : Fragment() {

    private lateinit var userTraining: Routine

    private var hasData: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userTraining = Datasource.user.training

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        hasData = userTraining.exercises.isEmpty()
        val layout = when(hasData) {
            true -> R.layout.fragment_exercise_empty
            false -> R.layout.fragment_exercise
        }

        return inflater.inflate(layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Bindings
        val routineNameView = view.findViewById<TextView>(R.id.routine_name)
        val listView = view.findViewById<RecyclerView>(R.id.routine_list)
        val video = view.findViewById<VideoView>(R.id.routine_video)

        Firebase.storage.reference.child("TestVideo.mp4").getFile(File.createTempFile("videos","mp4"))
            .addOnSuccessListener {
                video.setVideoURI(Uri.parse(""))
                video.setMediaController(MediaController(context))
            }
        routineNameView.text = resources.getString(R.string.routine_name, userTraining.name)
        listView.adapter = ExercisesAdapter(view.context, userTraining.exercises)
    }
}