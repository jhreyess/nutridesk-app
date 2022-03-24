package com.nutrikares.nutrideskapp.data

import android.view.Menu
import com.nutrikares.nutrideskapp.data.models.Exercise
import com.nutrikares.nutrideskapp.data.models.Food
import com.nutrikares.nutrideskapp.data.models.FoodDayMenu
import com.nutrikares.nutrideskapp.data.models.Routine

object Datasource {

    private val ingredients = listOf("Ingredients")
    private val steps = listOf("Steps")

    private val foods: List<Food> = listOf(
        Food("Desayuno", "Huevo", "Desc 1", 10, 0, ingredients, steps),
        Food("Comida", "Chilaquiles", "Desc 2", 10, 0, ingredients, steps),
        Food("Cena", "Molletes", "Desc 3", 10, 0, ingredients, steps),
        Food("Snack", "Avena", "Desc 4", 10, 0, ingredients, steps),
        Food("Snack", "Fruta", "Desc 5", 10, 0, ingredients, steps),
    )

    val weekStart: String = "21 Mar"
    val weekEnd: String = "27 Mar"

    val userWeight = 64
    val userIMC = 19

    private val exercises: List<Exercise> = listOf(
        //Exercise("Sentadillas", 3, 20)
    )

    val weekMenu: List<FoodDayMenu> = listOf(
        FoodDayMenu("Lunes", 0, foods),
        FoodDayMenu("Martes", 0, foods),
        FoodDayMenu("Miércoles", 0, foods),
        FoodDayMenu("Jueves", 0, foods),
        FoodDayMenu("Viernes", 0, foods),
        FoodDayMenu("Sábado", 0, foods),
        FoodDayMenu("Domingo", 0, foods),
    )

    val getExercisesSize = exercises.size
}