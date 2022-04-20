package com.nutrikares.nutrideskapp.data.models

//data class User(
//    var info: UserInfo = UserInfo(),
//    var diets: List<Food> = listOf(),
//    var training: Routine = Routine()
//)

data class User(
    var info: UserInfo = UserInfo(),
    var diets: UserDiets = UserDiets(),
    var routines: UserRoutines = UserRoutines()
)

data class UserInfo(
    var name: String = "",
    var age: Int = 0,
    var role: String = "",
    var stats: Stats = Stats()
)

data class UserDiets(
    var diets: MutableMap<String, List<Food>> = mutableMapOf()
)

data class UserRoutines(
    var routines: Map<String, Routine> = mapOf()
)

data class Stats(
    var weight: Double = 0.0,
    var waist: Double = 0.0,
    var imc: Double = 0.0,
    var abs: Double = 0.0,
    var hip: Double = 0.0,
    var progress: HashMap<String, Double> = hashMapOf()
)


data class UnLoggedUser(private var email: String, private var password: String ){
    fun getEmail() = email
    fun getPassword() = password
}