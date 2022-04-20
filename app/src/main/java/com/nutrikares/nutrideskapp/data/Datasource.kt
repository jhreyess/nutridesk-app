package com.nutrikares.nutrideskapp.data

import android.net.Uri
import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nutrikares.nutrideskapp.data.models.*
import java.lang.Exception

object Datasource {
    private lateinit var database : DatabaseReference
    lateinit var user: User
    private val ingredients = mutableListOf("Ingredients")
    private val steps = mutableListOf("Steps")
    private var currentRecipe =  Food("",10, "", "",  "", ingredients, steps)
    var newRecipe =  Food("",10, "", "", "",  ingredients, steps)
    var newRecipeId = ""
    fun setCurrentRecipe(recipe : Food){
        currentRecipe = recipe
    }

    fun getCurrentRecipe():Food{
        return currentRecipe
    }

    init{
        database = Firebase.database.reference
    }

    private val foods: List<Food> = listOf(
        Food("Desayuno",15, "Huevo", "Desc 1",  "", ingredients, steps),
        Food("Comida", 30,"Chilaquiles", "Desc 2",  "", ingredients, steps),
        Food("Cena", 30,"Molletes", "Desc 3",  "", ingredients, steps),
        Food("Snack", 15,"Avena", "Desc 4", "", ingredients, steps),
        Food("Snack", 10,"Fruta", "Desc 5", "", ingredients, steps),
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

    /*Tengo dos colecciones porque en una se guarda la key, que es el necesario para poder consultar la b de d
    y en la otra las puras strings pq no supe como usar el HashMap en el Adapter.*/

    fun getPatients(): MutableList<String>{
        database.child("users").get().addOnSuccessListener {
            mapOfPatients.clear()
            patients.clear()
            for (ds in it.children) {
                if(ds.child("role").getValue().toString().equals("patient")){
                    mapOfPatients.put(ds.child("name").getValue().toString(),ds.key.toString())
                    patients.add(ds.child("name").getValue().toString())
                }
            }
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
        return patients
    }

    fun getRecipes():MutableList<String>{
        database.child("recipes").get().addOnSuccessListener {
            mapOfRecipes.clear()
            recipes.clear()
            for (ds in it.children) {
                //var recipe : Food = ds.getValue(Food::class.java)!!
                mapOfRecipes.put(ds.child("name").getValue().toString(),ds.key.toString())
                recipes.add(ds.child("name").getValue().toString())
            }
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
        return recipes
    }

    /*fun getRoutines():MutableList<String>{
        database.child("trainings").get().addOnSuccessListener {
            mapOfRoutines.clear()
            routines.clear()
            for (ds in it.children) {
                mapOfRoutines.put(ds.key.toString(),ds.child("name").getValue().toString())
                routines.add(ds.child("name").getValue().toString())
            }
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
        return recipes
    }*/

    val mapOfPatients: HashMap<String, String> = hashMapOf()
    private val patients: MutableList<String> = mutableListOf()

    val mapOfRecipes: HashMap<String, String> = hashMapOf()
    private val recipes:MutableList<String> = mutableListOf()

    val mapOfRoutines: HashMap<String, String> = hashMapOf()
    val routines: MutableList<String> = mutableListOf()

    private var clickOnRecipe = false

    fun getClickOnRecipe():Boolean{
        return clickOnRecipe
    }
    fun setClickOnRecipe(value : Boolean){
        clickOnRecipe = value
    }

    fun queryRecipe(key:String){
        database.child("recipes").child(key).get().addOnSuccessListener {
            currentRecipe= it.getValue(Food::class.java)!!
            //Log.v("Firebase",it.value.toString())
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }

    fun addRecipe():Boolean{
        try{
            database.child("recipes").child(newRecipeId).setValue(newRecipe)
            return true
        }catch (e : Exception){
            Log.d("Exception",e.toString())
            return false
        }
    }
    fun updateRecipe():Boolean{
        try{
            database.child("recipes").child(mapOfRecipes[currentRecipe.name].toString()).setValue(newRecipe)
            return true
        }catch (e : Exception){
            Log.d("Exception",e.toString())
            return false
        }
    }
}