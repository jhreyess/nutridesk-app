package com.nutrikares.nutrideskapp.data.models

import android.net.Uri
import androidx.annotation.DrawableRes

data class Food (
    var type: String = "",
    var time: Int = 0,
    var name: String = "",
    var description: String = "",
    var imageResourceId: String = "",
    var ingredients: MutableList<String> = mutableListOf(),
    var steps: MutableList<String> = mutableListOf(),
    var info: Nutrients= Nutrients()
)

data class FoodDayMenu (
    val id: String = "",
    @DrawableRes val imageResourceId: Int = 0,
    val foods: List<Food> = listOf()
)

data class Nutrients(
    var calories:Int = 0,
    var carbs:Int = 0,
    var fats:Int=0,
    var protein:Int=0
)

data class FoodIdMap(
    var meal: String = "",
    var breakfast: String = "",
    var dinner: String = "",
    var snack1: String = "",
    var snack2: String = ""
)