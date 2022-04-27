package com.nutrikares.nutrideskapp.data.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Food (
    var type: String = "",
    var time: Int = 0,
    var name: String = "",
    var description: String = "",
    var imageResourceId: String = "",
    var ingredients: MutableList<String> = mutableListOf(),
    var steps: MutableList<String> = mutableListOf(),
    var info: Nutrients = Nutrients()
){
    fun translateFoodType() : String{
        return when(type){
            "breakfast" -> "Desayuno"
            "meal" -> "Comida"
            "dinner" -> "Cena"
            "snack1" -> "Snack"
            "snack2" -> "Snack"
            else -> "Sin asignar"
        }
    }
}

data class Nutrients(
    var carbs: Int = 0,
    var fats: Int = 0,
    var calories: Int = 0,
    var protein: Int = 0
)

data class FoodDayMenu (
    var day: String = "",
    var imageUri: String = "",
    var foods: MutableMap<String, Food> = mutableMapOf(
        "breakfast" to Food(),
        "dinner" to Food(),
        "meal" to Food(),
        "snack1" to Food(),
        "snack2" to Food()
    )
)

data class FoodWeekMenu(
    var weekStart: String = "",
    var weekEnd: String = "",
    var days: MutableMap<String, FoodDayMenu> = mutableMapOf(
        "monday" to FoodDayMenu(),
        "tuesday" to FoodDayMenu(),
        "wednesday" to FoodDayMenu(),
        "thursday" to FoodDayMenu(),
        "friday" to FoodDayMenu(),
        "saturday" to FoodDayMenu(),
        "sunday" to FoodDayMenu()
    )
)

private object FoodType {
    const val BREAKFAST = 0
    const val DINNER = 1
    const val MEAL = 2
    const val SNACK1 = 3
    const val SNACK2 = 4
}

val mealIndex = {x: String -> when(x){
    "breakfast" -> FoodType.BREAKFAST
    "dinner" -> FoodType.DINNER
    "meal" -> FoodType.MEAL
    "snack1" -> FoodType.SNACK1
    "snack2" -> FoodType.SNACK2
    else -> 5
}}
