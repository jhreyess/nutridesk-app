package com.nutrikares.nutrideskapp.data.models

import android.net.Uri
import com.google.firebase.database.IgnoreExtraProperties

data class Exercise(
    val name: String = "",
    val sets: Int = 0,
    val reps: Int = 0
)

@IgnoreExtraProperties
data class Routine(
    var id: String = "",
    var name: String = "",
    var exercises: MutableList<Exercise> = mutableListOf(),
    var videoUri: Uri? = null,
    var videoPath: String = ""
)