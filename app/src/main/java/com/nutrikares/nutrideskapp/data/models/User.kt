package com.nutrikares.nutrideskapp.data.models

data class User(
    var name: String = "",
    var role: String = "",
    var stats: Stats = Stats(),
    var diets: List<Food> = listOf(),
    var training: Routine = Routine()
)

data class Stats(
    var weight: Double = 0.0,
    var waist: Double = 0.0,
    var imc: Double = 0.0,
    var abs: Double = 0.0,
    var hip: Double = 0.0,
    var progress: HashMap<String, Double> = hashMapOf()
)

class Entry(x: String, y: Double) : com.github.mikephil.charting.data.Entry()


data class UnLoggedUser(private var email: String, private var password: String ){
    fun getEmail() = email
    fun getPassword() = password
}