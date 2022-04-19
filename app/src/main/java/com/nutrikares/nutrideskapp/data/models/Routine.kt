package com.nutrikares.nutrideskapp.data.models

data class Exercise(
    val name: String = "",
    val sets: Int = 0,
    val reps: Int = 0
)

data class Routine(
    var id: String = "",
    var name: String = "",
    var exercises: List<Exercise> = listOf()
)