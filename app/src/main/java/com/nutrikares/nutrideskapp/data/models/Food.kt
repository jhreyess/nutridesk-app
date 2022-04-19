package com.nutrikares.nutrideskapp.data.models

import androidx.annotation.DrawableRes

data class Food (
    val type: String = "",
    val name: String = "",
    val description: String = "",
    val preparationTime: Int = 0,
    @DrawableRes val imageResourceId: Int = 0,
    val ingredients: List<String> = listOf(),
    val steps: List<String> = listOf()
)

data class FoodDayMenu (
    val id: String = "",
    @DrawableRes val imageResourceId: Int = 0,
    val foods: List<Food> = listOf()
)