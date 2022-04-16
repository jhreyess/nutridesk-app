package com.nutrikares.nutrideskapp.data

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


    val patients: MutableList<String> = mutableListOf(
        "Gala Vaquero Madrigal",
        "Pili Ainara Cardona Escalona",
        "Carmina Tamara Plana Lillo",
        "Merche Berrocal Cardona",
        "Teófilo Coronado-Giménez",
        "Gracia Castillo-Grau",
        "Montserrat Gibert-Barceló",
        "Félix Bustos Machado",
        "Héctor Cobos España",
        "Olimpia Ferrán",
        "Abraham Román Cuadrado",
        "María Cueto Cazorla",
        "Haydée Somoza-España",
        "Jose Carlos Yuste-Cabello",
        "Patricio Belda Portero",
    )

    val recipes: MutableList<String> = mutableListOf(
        "Receta 1",
        "Receta 2",
        "Receta 3",
        "Receta 4",
        "Receta 5",
        "Receta 6",
        "Receta 7",
        "Receta 8",
        "Receta 9",
        "Receta 10",
        "Receta 11",
        "Receta 12",
        "Receta 13",
        "Receta 14",
        "Receta 15",
        "Receta 16"
    )
    val routines: MutableList<String> = mutableListOf(
        "Rutina 1",
        "Rutina 2",
        "Rutina 3",
        "Rutina 4",
        "Rutina 5",
        "Rutina 6",
        "Rutina 7",
        "Rutina 8",
        "Rutina 9",
        "Rutina 10",
        "Rutina 11",
        "Rutina 12",
        "Rutina 13",
        "Rutina 14",
        "Rutina 15",
        "Rutina 16"
    )
}