package com.nutrikares.nutrideskapp.ui.routines

import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.util.Util
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.nutrikares.nutrideskapp.R
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.data.models.Exercise
import com.nutrikares.nutrideskapp.databinding.FragmentCreateRoutineBinding
import java.lang.Exception

class CreateRoutineFragment : Fragment() {

    private var _binding: FragmentCreateRoutineBinding? = null
    private val binding get() = _binding!!
    private var excercises : MutableList<Exercise> = mutableListOf()

    //Firebase
    private val storage : FirebaseStorage = FirebaseStorage.getInstance()
    var database : DatabaseReference = Firebase.database.reference

    //Pick Video
    private val VIDEO_PICK_GALLERY_CODE =100
    private val VIDEO_PICK_CAMERA_CODE =101
    private val CAMERA_REQUEST_CODE =102
    private lateinit var cameraPermissions:Array<String>
    lateinit var videoUri : Uri
    private var clickDone = false
    private lateinit var progressDialog : ProgressDialog
    lateinit var downloadUri : Uri
    private var displayVideoUri : Uri? = null

    //Exo
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition = 0L
    private var player: ExoPlayer? = null
    private lateinit var videoView: StyledPlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraPermissions = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateRoutineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        videoUri=Uri.parse("")
        downloadUri=Uri.parse("")
        clickDone=false

        //Pick VIDEO
        progressDialog = ProgressDialog(activity)
        progressDialog.setTitle("Por favor espera")
        progressDialog.setMessage("Subiendo video")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.uploadVideoButton.setOnClickListener{
            clickDone=true
            videoPickGallery()
        }

        if(Datasource.getClickOnRoutine()) {
            val routine = Datasource.getCurrentRoutine()
            excercises=routine.exercises
            val storageRef = Firebase.storage.reference
            storageRef.child("videos").child(routine.videoPath).downloadUrl.addOnSuccessListener {uri ->
                downloadUri = uri
                if(!downloadUri.toString().equals("")){
                    displayVideoUri = downloadUri
                    videoView = view.findViewById(R.id.routineVideo_videoView)
                    initializePlayer(displayVideoUri!!)

                    displayVideoUri?.let {
                        val defaultHeight = videoView.layoutParams.height
                        videoView.setControllerOnFullScreenModeChangedListener {fullscreen ->
                            if(fullscreen){
                                videoView.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
                            }else{
                                videoView.layoutParams.height = defaultHeight
                            }
                        }
                    }
                }
            }
            binding.routineNameEditText.setText(routine.name)
            binding.routineIdEditText.setText(routine.id)
            binding.routineIdEditText.isFocusable = false
            binding.listOfExcercisesTextView.setText(convertListIntoString(excercises))
        }

        binding.addExcerciseImageView.setOnClickListener{
            addExercise()
        }

        binding.removeExcerciseImageView.setOnClickListener{
            removeExercise()
        }

        binding.acceptButton.setOnClickListener {
            if(checkFields()){
                val lastName = Datasource.getCurrentRoutine().name
                var nameRepeated=false
                var idRepeated=false
                if(Datasource.getClickOnRoutine()){
                    //Modificación
                    for (routine in Datasource.routines){
                        if(!lastName.equals(binding.routineNameEditText.text.toString())){
                            if (routine.equals(binding.routineNameEditText.text.toString())) nameRepeated = true
                        }
                    }
                    if (!nameRepeated){
                        uploadRoutineData()
                    }else{
                        Toast.makeText(activity, "El nombre ya existe", Toast.LENGTH_LONG).show();
                    }
                }else{
                    //Es adición
                    for (routine in Datasource.routines){
                        if (routine.equals(binding.routineNameEditText.text.toString())) nameRepeated = true
                        if (Datasource.mapOfRoutines[routine].equals(binding.routineIdEditText.text.toString())) idRepeated = true
                    }
                    if(nameRepeated || idRepeated){
                        Toast.makeText(activity, "El nombre o id ya existen", Toast.LENGTH_LONG).show();
                    }else{
                        uploadRoutineData()
                    }
                }
            }else{
                Toast.makeText(activity, "Faltan datos por llenar", Toast.LENGTH_LONG).show();
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //Exo
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
        if ((Util.SDK_INT >= 24) && (displayVideoUri!=null)) {
            if(player==null) {
                initializePlayer(displayVideoUri!!)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if ((Util.SDK_INT < 24 || player == null) && (displayVideoUri!=null)) {
            initializePlayer(displayVideoUri!!)
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
    //

    private fun uploadRoutineData() {
        if(!videoUri.toString().equals("") && clickDone){
            progressDialog.show()
            val filePathAndName = "videos/${videoUri.lastPathSegment}"
            val storageReference = storage.getReference(filePathAndName)
            val file = videoUri
            val uploadTask = storageReference.putFile(file)

            uploadTask.addOnFailureListener{
                progressDialog.dismiss()
                Toast.makeText(activity, "Error al cargar el video", Toast.LENGTH_SHORT).show();
            }.addOnSuccessListener {
                downloadUri = Uri.parse(videoUri.lastPathSegment)
                attachData()
                if(Datasource.getClickOnRoutine()){
                    progressDialog.dismiss()
                    //Es modificación
                    updateRoutine()
                }else{
                    progressDialog.dismiss()
                    //Es adición
                    addRoutine()
                }
                Toast.makeText(activity, "Video cargado", Toast.LENGTH_SHORT).show();
            }
        }else{
            attachData()
            updateRoutine()
        }
    }

    fun attachData(){
        Datasource.newRoutine.name = binding.routineNameEditText.text.toString()
        Datasource.newRoutine.id = binding.routineIdEditText.text.toString()
        Datasource.newRoutine.exercises = excercises
        Datasource.newRoutine.videoPath = if(!videoUri.toString().equals("") && clickDone) downloadUri.toString() else  Datasource.getCurrentRoutine().videoPath
    }

    fun addRoutine(){
        try{
            database.child("trainings").child(Datasource.newRoutine.id).setValue(Datasource.newRoutine)
            Toast.makeText(activity, "Rutina agregada exitosamente", Toast.LENGTH_LONG).show();
            findNavController().navigate(R.id.action_createRoutineFragment_to_nav_routines)
        }catch (e : Exception){
            Log.d("Exception",e.toString())
            Toast.makeText(activity, "La rutina no pudo ser agregada", Toast.LENGTH_LONG).show();
        }
    }

    fun updateRoutine(){
        try{
            database.child("trainings").child(Datasource.getCurrentRoutine().id).setValue(Datasource.newRoutine)
            Toast.makeText(activity, "Rutina modificada exitosamente", Toast.LENGTH_LONG).show();
            findNavController().navigate(R.id.action_createRoutineFragment_to_nav_routines)
        }catch (e : Exception){
            Log.d("Exception",e.toString())
            Toast.makeText(activity, "La Rutina no pudo ser modificada", Toast.LENGTH_LONG).show();
        }
    }

    fun checkFields():Boolean{
        return !(binding.routineIdEditText.text.isEmpty() ||
                binding.routineNameEditText.text.isEmpty() ||
                binding.listOfExcercisesTextView.text.isEmpty() ||
                ((videoUri.toString().equals("")) && (downloadUri.toString().equals(""))))
    }


    fun addExercise(){
        if(!(binding.routineExNameEditText.text.isEmpty() || binding.routineRepEditText.text.isEmpty() || binding.routineSeriesEditText.text.isEmpty())){
            val name = binding.routineExNameEditText.text.toString()
            val reps =  Integer.parseInt(binding.routineRepEditText.text.toString())
            val series = Integer.parseInt(binding.routineSeriesEditText.text.toString())
            val exercise = Exercise(name, series, reps)
            excercises.add(exercise)
            binding.listOfExcercisesTextView.setText(convertListIntoString(excercises))
            binding.routineExNameEditText.setText("")
            binding.routineRepEditText.setText("")
            binding.routineSeriesEditText.setText("")
        }
    }

    fun removeExercise(){
        if(!(excercises.size==0)){
            excercises.removeLast()
            binding.listOfExcercisesTextView.setText(convertListIntoString(excercises))
        }
    }

    fun convertListIntoString(lista : MutableList<Exercise>) : String{
        var cadena = ""
        lista.forEach{
            cadena+="${it.name} - ${it.sets} series - ${it.reps} reps\n"
        }
        return cadena
    }

    private fun setVideoToVideoView() {
        displayVideoUri = videoUri
        videoView = view?.findViewById(R.id.routineVideo_videoView)!!
        initializePlayer(displayVideoUri!!)

        displayVideoUri?.let {
            val defaultHeight = videoView.layoutParams.height
            videoView.setControllerOnFullScreenModeChangedListener {fullscreen ->
                if(fullscreen){
                    videoView.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
                }else{
                    videoView.layoutParams.height = defaultHeight
                }
            }
        }
    }

     fun videoPickGallery(){
        val intent = Intent()
        intent.type = "video/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(
            Intent.createChooser(intent, "Choose video"),
            VIDEO_PICK_GALLERY_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK){
            if(requestCode == VIDEO_PICK_CAMERA_CODE || requestCode == VIDEO_PICK_GALLERY_CODE){
                videoUri = data!!.data!!
                Log.v("Video",videoUri.toString())
                setVideoToVideoView()
            }
        }else{
            Toast.makeText(activity, "Cancelled", Toast.LENGTH_SHORT).show()
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}

