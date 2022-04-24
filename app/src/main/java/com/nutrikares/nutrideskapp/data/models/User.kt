package com.nutrikares.nutrideskapp.data.models

data class User(
    var info: UserInfo = UserInfo(),
    var diets: FoodWeekMenu = FoodWeekMenu(),
    var routine: Routine = Routine(),
    var routines:MutableMap<String, Routine> = mutableMapOf(
        "monday" to Routine(),
        "tuesday" to Routine(),
        "wednesday" to Routine(),
        "thursday" to Routine(),
        "friday" to Routine(),
        "saturday" to Routine(),
        "sunday" to Routine()
    )
)

data class UserInfo(
    var name: String = "",
    var age: Int = 0,
    var role: String = "",
    var objective:String = "",
    var stats: Stats = Stats()
)

data class Stats(
    var weight: Double = 0.0,
    var waist: Double = 0.0,
    var imc: Double = 0.0,
    var abs: Double = 0.0,
    var hip: Double = 0.0,
    var progress: List<Int> = mutableListOf(0),
    var relative: List<Int> = mutableListOf(0)
)

data class UnLoggedUser(private var email: String, private var password: String ){
    fun getEmail() = email
    fun getPassword() = password
}