package com.nutrikares.nutrideskapp.data

import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nutrikares.nutrideskapp.data.models.*
import kotlin.collections.HashMap

object Datasource {

    private var database : DatabaseReference = Firebase.database.reference

    private var user = User()

    fun setUserInfo(info: UserInfo){ user.info = info }
    fun setUserDiets(diets: FoodWeekMenu){ user.diets = diets }
    fun setUserRoutines(routine: Routine){ user.routine = routine }

    fun getUserInfo() = user.info
    fun getUserRoutines() = user.routine
    fun getUserDiets() = user.diets

    private val usersList: MutableList<UserInfo?> = mutableListOf()
    private val routinesList: MutableList<Routine?> = mutableListOf()
    private val foodsList: MutableList<Food?> = mutableListOf()

    fun getUsers() = usersList

    /* TODO: BORRAR DATOS DE PRUEBA */
    private val ingredients = mutableListOf("Ingredients")
    private val steps = mutableListOf("Steps")
    private var currentRecipe = Food("", 10, "", "", "", ingredients, steps)
    private var currentRoutine = Routine()
    private var currentUser = User()
    var newRecipe =  Food("",10, "", "", "",  ingredients, steps)
    var newRecipeId = ""
    var newRoutine = Routine("","", mutableListOf(), "")
    var newUser = User()

    fun setCurrentUser(user:User){
        currentUser = user
    }

    fun getCurrentUser() : User{
        return currentUser
    }

    fun setCurrentRecipe(recipe : Food){
        currentRecipe = recipe
    }

    fun getCurrentRecipe():Food{
        return currentRecipe
    }

    fun setCurrentRoutine(routine : Routine){
        currentRoutine = routine
    }

    fun getCurrentRoutine():Routine{
        return currentRoutine
    }
    /* TODO: BORRAR DATOS DE PRUEBA */

    val mapOfPatients: HashMap<String, String> = hashMapOf()
    val patients: MutableList<String> = mutableListOf()

    val mapOfRecipes: HashMap<String, String> = hashMapOf()
    val recipes:MutableList<String> = mutableListOf()

    val mapOfRoutines: HashMap<String, String> = hashMapOf()
    val routines: MutableList<String> = mutableListOf()

    private var clickOnRecipe = false

    fun getClickOnRecipe():Boolean{
        return clickOnRecipe
    }
    fun setClickOnRecipe(value : Boolean){
        clickOnRecipe = value
    }

    private var clickOnRoutine = false

    fun getClickOnRoutine():Boolean{
        return clickOnRoutine
    }
    fun setClickOnRoutine(value : Boolean){
        clickOnRoutine = value
    }

}