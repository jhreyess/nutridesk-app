package com.nutrikares.nutrideskapp

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.util.Util
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.nutrikares.nutrideskapp.adapters.ExercisesAdapter
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.data.models.Routine

class FragmentExercise : Fragment() {

    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition = 0L

    private var player: ExoPlayer? = null

    private lateinit var videoView: StyledPlayerView
    private var videoUri: Uri? = null

    private lateinit var userTraining: Routine

    private var hasData: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userTraining = Datasource.getUserRoutines()
        hasData = userTraining.exercises.isNotEmpty()
        videoUri = userTraining.videoUri
        val database = Firebase.database.reference
        database.child("trainings")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layout = when(hasData) {
            true -> R.layout.fragment_exercise
            false -> R.layout.fragment_exercise_empty
        }

        return inflater.inflate(layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(hasData) {
            // Bindings
            val routineNameView = view.findViewById<TextView>(R.id.routine_name)
            val listView = view.findViewById<RecyclerView>(R.id.routine_list)
            val label = view.findViewById<TextView>(R.id.fragment_label)
            videoView = view.findViewById(R.id.routine_video)

            if(videoUri == null){
                Firebase.storage.reference.child("videos").child(userTraining.videoPath).downloadUrl
                    .addOnSuccessListener { uri ->
                        Datasource.getUserRoutines().videoUri = uri
                        initializePlayer(uri)
                    }.addOnFailureListener {
                        activity?.runOnUiThread{
                            Toast.makeText(activity,"No es posible reproducir el video",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
            }

            videoUri?.let {
                val defaultHeight = videoView.layoutParams.height
                videoView.setControllerOnFullScreenModeChangedListener {fullscreen ->
                    if(fullscreen){
                        videoView.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
                        label.visibility = View.GONE
                        listView.visibility = View.GONE
                    }else{
                        videoView.layoutParams.height = defaultHeight
                        label.visibility = View.VISIBLE
                        listView.visibility = View.VISIBLE
                    }
                }
            }

            routineNameView.text = resources.getString(R.string.routine_name, userTraining.name)
            listView.adapter = ExercisesAdapter(view.context, userTraining.exercises)
        }
    }

    private fun initializePlayer(uri: Uri) {
        player = context?.let {
        ExoPlayer.Builder(it)
                .build()
                .also { exoPlayer ->
                    videoView.player = exoPlayer
                    exoPlayer.setMediaItem(MediaItem.fromUri(uri))
                    exoPlayer.playWhenReady = playWhenReady
                    exoPlayer.seekTo(currentWindow, playbackPosition)
                    exoPlayer.prepare()
                }
        }
    }

    private fun releasePlayer() {
        player?.run {
            playbackPosition = this.currentPosition
            currentWindow = this.currentMediaItemIndex
            playWhenReady = this.playWhenReady
            release()
        }
        player = null
    }

    override fun onStart() {
        super.onStart()
        if ((Util.SDK_INT >= 24) && (videoUri!=null)) {
            if(player==null) {
                initializePlayer(videoUri!!)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if ((Util.SDK_INT < 24 || player == null) && (videoUri!=null)) {
            initializePlayer(videoUri!!)
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }


    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }

}