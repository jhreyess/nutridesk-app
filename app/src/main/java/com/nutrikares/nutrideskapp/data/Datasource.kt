package com.nutrikares.nutrideskapp.data

import android.util.Log
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nutrikares.nutrideskapp.data.models.*
import com.nutrikares.nutrideskapp.utils.Calendar
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

    private val users: List<User> = listOf()
    private val trainings: List<Routine> = listOf()
    private val foods: MutableList<Food> = mutableListOf()

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

    private fun retrieveAllData(){
        val usersRef = database.child("users")
        val dietsRef = database.child("diets")
        val trainingsRef = database.child("trainings")

        /*val usersListener = object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }

        val dietsListener = object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }

        val routinesListener = object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }

        usersRef.addValueEventListener(usersListener)
        dietsRef.addValueEventListener(dietsListener)
        trainingsRef.addValueEventListener(routinesListener)*/
    }

    private fun retrieveUserData(uid: String){

        val today = Calendar().getDate("en")
        val userDietsRef = database.child("users_diets").child(uid)
        val userTrainingsRef = database.child("users_trainings").child(uid).child(today)

        userDietsRef.addValueEventListener ( object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val weekMenu = snapshot.getValue(FoodWeekMenu::class.java)!!
                val meals = weekMenu.days
                meals.map { it.value.day = Calendar().translate(it.key) }
                val days = weekMenu.days.toSortedMap(compareBy { Calendar().dateIndex(it) })
                days.values.forEach { it.foods.toSortedMap(compareBy { type -> mealIndex(type) })}
                val diets = FoodWeekMenu(weekMenu.weekStart, weekMenu.weekEnd, days)
                setUserDiets(diets)
            }
            override fun onCancelled(error: DatabaseError) { }
        })


        userTrainingsRef.addValueEventListener(object:  ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    val routine = snapshot.getValue(Routine::class.java)!!
                    setUserRoutines(routine)
                }
            }
            override fun onCancelled(error: DatabaseError) { }
        })
    }

    fun retrieveData(role: String, id: String) {
        if(role == "admin") {
            retrieveAllData()
        } else {
            retrieveUserData(id)
        }
    }
}