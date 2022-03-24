package com.nutrikares.nutrideskapp.data.models

data class Exercise(
    val name: String,
    val sets: Int,
    val reps: Int,
)

data class Routine(
    val id: String,
    val name: String,
    val exercises: List<Exercise>
)