package com.nutrikares.nutrideskapp.data.models

import androidx.annotation.DrawableRes

data class Food (
    val type: String,
    val name: String,
    val description: String,
    val preparationTime: Int,
    @DrawableRes val imageResourceId: Int,
    val ingredients: List<String>,
    val steps: List<String>
)

data class FoodDayMenu (
    val id: String,
    @DrawableRes val imageResourceId: Int,
    val foods: List<Food>,
)