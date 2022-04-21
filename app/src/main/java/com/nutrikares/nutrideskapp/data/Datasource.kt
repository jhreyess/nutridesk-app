package com.nutrikares.nutrideskapp.data

import android.util.Log
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nutrikares.nutrideskapp.data.models.*
import java.lang.Exception
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
    var newRecipe =  Food("",10, "", "", "",  ingredients, steps)
    var newRecipeId = ""

    fun setCurrentRecipe(recipe : Food){
        currentRecipe = recipe
    }

    fun getCurrentRecipe():Food{
        return currentRecipe
    }
    /* TODO: BORRAR DATOS DE PRUEBA */

    @JvmName("getPatients1")
    fun getPatients(): MutableList<String>{
        database.child("users").get().addOnSuccessListener {
            mapOfPatients.clear()
            patients.clear()
            for (ds in it.children) {
                if(ds.child("role").value.toString() == "patient"){
                    mapOfPatients.put(ds.child("name").value.toString(),ds.key.toString())
                    patients.add(ds.child("name").value.toString())
                }
            }
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
        return patients
    }

    @JvmName("getRecipes1")
    fun getRecipes():MutableList<String>{
        database.child("recipes").get().addOnSuccessListener {
            mapOfRecipes.clear()
            recipes.clear()
            for (ds in it.children) {
                //var recipe : Food = ds.getValue(Food::class.java)!!
                mapOfRecipes.put(ds.child("name").value.toString(),ds.key.toString())
                recipes.add(ds.child("name").value.toString())
            }
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
        return recipes
    }

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

    fun queryRecipe(key:String){
        database.child("recipes").child(key).get().addOnSuccessListener {
            currentRecipe= it.getValue(Food::class.java)!!
            //Log.v("Firebase",it.value.toString())
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }

    fun addRecipe():Boolean{
        return try{
            database.child("recipes").child(newRecipeId).setValue(newRecipe)
            true
        }catch (e : Exception){
            Log.d("Exception",e.toString())
            false
        }
    }
    fun updateRecipe():Boolean{
        return try{
            database.child("recipes").child(mapOfRecipes[currentRecipe.name].toString()).setValue(newRecipe)
            true
        }catch (e : Exception){
            Log.d("Exception",e.toString())
            false
        }
    }
}