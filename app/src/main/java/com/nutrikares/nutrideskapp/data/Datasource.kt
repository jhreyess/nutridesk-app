package com.nutrikares.nutrideskapp.data

import android.net.Uri
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nutrikares.nutrideskapp.data.models.*
import java.lang.Exception

object Datasource {

    private var database : DatabaseReference = Firebase.database.reference
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

    private var user = User()

    fun setUserInfo(info: UserInfo){ user.info = info }
    fun setUserRoutines(routines: UserRoutines){ user.routines = routines }
    fun setUserDiets(diets: UserDiets){ user.diets = (diets) }

    fun getUserInfo() = user.info
    fun getUserRoutines() = user.routines
    fun getUserDiets() = user.diets

    var videoUri: Uri? = null
    private val users: List<User> = listOf()
    private val trainings: List<Routine> = listOf()
    
//    private val foods: List<Food> = listOf(
//        Food("Desayuno",0, "Huevo", "Desc 1", "", ingredients, steps),
//        Food("Comida", 0,"Chilaquiles", "Desc 2", "", ingredients, steps),
//        Food("Cena", 0,"Molletes", "Desc 3", "",ingredients, steps),
//        Food("Snack", 0,"Avena", "Desc 4", "", ingredients, steps),
//        Food("Snack",0, "Fruta", "Desc 5", "", ingredients, steps),
//    )

    private val foods: List<Food> = listOf(
        Food("Desayuno",15, "Huevo", "Desc 1",  "", ingredients, steps),
        Food("Comida", 30,"Chilaquiles", "Desc 2",  "", ingredients, steps),
        Food("Cena", 30,"Molletes", "Desc 3",  "", ingredients, steps),
        Food("Snack", 15,"Avena", "Desc 4", "", ingredients, steps),
        Food("Snack", 10,"Fruta", "Desc 5", "", ingredients, steps),
    )

    val weekStart: String = "21 Mar"
    val weekEnd: String = "27 Mar"

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

    fun retrieveAllData(){
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

    fun retrieveUserData(uid: String) {
        val userDietsRef = database.child("users_diets").child(uid)
        val dietsRef = database.child("recipes")
        val userTrainingsRef = database.child("users_trainings").child(uid)
        val trainingsRef = database.child("trainings")

        val userData = mutableMapOf<String, Any>()
        val userDiets: HashMap<String, Map<String, Food?>> = hashMapOf()

        userDietsRef.get().addOnCompleteListener { task ->
            if(task.isSuccessful){
                val weekMenu: MutableMap<String, Map<String, Food?>> = mutableMapOf()
                val data = task.result
                data.children.forEach { day ->
                    val weekday = day.key as String
                    val mealsIds = day.children.map { it.toString() }
                    val dayMenu: MutableMap<String, Food?> = mutableMapOf()
                    mealsIds.forEach { id ->
                        dietsRef.child(id).get().addOnSuccessListener {
                            val meal = it.getValue(Food::class.java)!!
                            dayMenu[meal.type] = meal
                        }
                    }
                    weekMenu[weekday] = dayMenu
                }
                Log.d("Debug", weekMenu.toString())
            }
        }
    }
}