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
    var exercises: MutableList<Exercise> = mutableListOf(
        Exercise("Example",0,0)
    ),
    var videoPath: String = "",
    var hasRoutines: Boolean = false
)

/*data class RoutineWeek(
    var days: MutableMap<String, Routine> = mutableMapOf(
        "monday" to Routine(),
        "tuesday" to Routine(),
        "wednesday" to Routine(),
        "thursday" to Routine(),
        "friday" to Routine(),
        "saturday" to Routine(),
        "sunday" to Routine()
    )
)*/