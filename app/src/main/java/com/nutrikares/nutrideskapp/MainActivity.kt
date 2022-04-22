package com.nutrikares.nutrideskapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.data.models.FoodWeekMenu
import com.nutrikares.nutrideskapp.data.models.Routine
import com.nutrikares.nutrideskapp.data.models.UserInfo
import com.nutrikares.nutrideskapp.data.models.mealIndex
import com.nutrikares.nutrideskapp.utils.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    private lateinit var database: DatabaseReference

    private lateinit var userRef: DatabaseReference
    private lateinit var userListener: ValueEventListener

    private lateinit var dietsRef: DatabaseReference
    private lateinit var dietsListener: ValueEventListener

    private lateinit var trainingRef: DatabaseReference
    private lateinit var trainingListener: ValueEventListener

    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = Firebase.database.reference
        uid = Firebase.auth.currentUser!!.uid

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navbar) as NavigationBarView
        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView.setOnApplyWindowInsetsListener(null)
        bottomNavigationView.setOnItemReselectedListener { navController.navigate(it.itemId) }
    }

    private fun startListeningUserQuery(){
        userRef = database.child("users").child(uid)
        userRef.keepSynced(true)

        userListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    Log.d("Debug", "Data fetched!")
                    val user = snapshot.getValue(UserInfo::class.java)!!
                    Datasource.setUserInfo(user)
                }
            }
            override fun onCancelled(error: DatabaseError) {  }
        }
        userRef.addValueEventListener(userListener)
    }

    private fun startListeningFoodQuery(){
        dietsRef = database.child("users_diets").child(uid)
        dietsRef.keepSynced(true)

        dietsListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val weekMenu = snapshot.getValue(FoodWeekMenu::class.java)!!
                val meals = weekMenu.days
                meals.map { it.value.day = Calendar().translate(it.key) }
                val days = weekMenu.days.toSortedMap(compareBy { Calendar().dateIndex(it) })
                days.values.forEach { it.foods.toSortedMap(compareBy { type -> mealIndex(type) })}
                val diets = FoodWeekMenu(weekMenu.weekStart, weekMenu.weekEnd, days)
                Datasource.setUserDiets(diets)
            }
            override fun onCancelled(error: DatabaseError) { }
        }
        dietsRef.addValueEventListener(dietsListener)
    }

    private fun startListeningRoutinesQuery(){
        val today = Calendar().getDate("en")
        trainingRef = database.child("users_trainings").child(uid).child(today)
        trainingRef.keepSynced(true)

        trainingListener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    val routine = snapshot.getValue(Routine::class.java)!!
                    Datasource.setUserRoutines(routine)
                }
            }
            override fun onCancelled(error: DatabaseError) { }
        }
        trainingRef.addValueEventListener(trainingListener)
    }

    private fun stopListening(){
        userRef.removeEventListener(userListener)
        dietsRef.removeEventListener(dietsListener)
        trainingRef.removeEventListener(trainingListener)
    }

    override fun onStart() {
        super.onStart()
        startListeningUserQuery()
        startListeningFoodQuery()
        startListeningRoutinesQuery()
    }

    override fun onStop() {
        super.onStop()
        stopListening()
    }
}