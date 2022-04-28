package com.nutrikares.nutrideskapp.ui.patients

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nutrikares.nutrideskapp.R
import com.nutrikares.nutrideskapp.adapters.AdapterListener
import com.nutrikares.nutrideskapp.adapters.AssignRecipeAdapter
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.data.models.*
import com.nutrikares.nutrideskapp.databinding.FragmentAssignRecipeBinding
import java.lang.Exception


class AssignRecipeFragment : Fragment() {
    private var _binding: FragmentAssignRecipeBinding? = null
    private val binding get() = _binding!!
    private var selectedRecipe : String = ""
    private lateinit var database : DatabaseReference
    lateinit var user: User
    lateinit var listOfRecipes : MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Firebase.database.reference
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAssignRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        user = Datasource.getCurrentUser()
        val currentDay = user.diets.days[Datasource.recipeDaySelected]
        val currentFood = currentDay?.foods?.get(Datasource.foodSelected)
        if (currentFood != null) {
            if (currentFood.name.equals("")){
                binding.selectedRecipeTextView.text= resources.getString(R.string.selected_text, "")
            }else{
                binding.selectedRecipeTextView.text = resources.getString(R.string.selected_text,currentFood.name)
            }
        }

        getRecipes()

        binding.searchRoutineIcon.setOnClickListener {
            searchRecipes(binding.searchRecipeEditText.text.toString())
        }

        binding.assignButton.setOnClickListener{
            if(!selectedRecipe.equals("")){
                setRecipe()
            }else{
                Toast.makeText(activity, "No hay una receta seleccionada", Toast.LENGTH_LONG).show();
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun searchRecipes(text : String){
        if(text.isEmpty()){
            binding.recipesRecycler.adapter = AssignRecipeAdapter(listOfRecipes, object : AdapterListener {
                override fun onItemClick(p0: String) {
                    selectedRecipe = p0
                    binding.selectedRecipeTextView.text = resources.getString(R.string.selected_text, p0)
                }
            })
            binding.recipesRecycler.setHasFixedSize(true)
        }else{
            val newList = mutableListOf<String>()
            for(recipe in listOfRecipes){
                if(recipe.contains(text, true)){
                    newList.add(recipe)
                }
            }
            binding.recipesRecycler.adapter = AssignRecipeAdapter(newList, object : AdapterListener {
                override fun onItemClick(p0: String) {
                    selectedRecipe = p0
                    binding.selectedRecipeTextView.text = resources.getString(R.string.selected_text, p0)
                }
            })
            binding.recipesRecycler.setHasFixedSize(true)
        }
    }

    fun getRecipes(){
        Log.d("Recipes","Entró")
        val database : DatabaseReference = Firebase.database.reference
        listOfRecipes = mutableListOf()

        val type =  when(Datasource.foodSelected){
            "breakfast" -> "Desayuno"
            "meal" -> "Comida"
            "dinner" -> "Cena"
            "snack1" -> "Snack"
            "snack2" -> "Snack"
            else -> "Desayuno"
        }

        database.child("recipes").get().addOnSuccessListener {
            if(Datasource.recipes.size == 0){
                Datasource.mapOfRecipes.clear()
                for (ds in it.children) {
                    Datasource.mapOfRecipes.put(ds.child("name").value.toString(),ds.key.toString())
                    Datasource.recipes.add(ds.child("name").value.toString())
                }
            }

            for (ds in it.children) {
                if(ds.child("type").value.toString().equals(type))
                listOfRecipes.add(ds.child("name").value.toString())
            }
            // Recycler Viewer
            binding.recipesRecycler.adapter = AssignRecipeAdapter(listOfRecipes, object : AdapterListener {
                override fun onItemClick(p0: String) {
                    selectedRecipe = p0
                    binding.selectedRecipeTextView.text = resources.getString(R.string.selected_text, p0)
                }
            })
            binding.recipesRecycler.setHasFixedSize(true)

        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }

    fun setRecipe(){
        val key = Datasource.mapOfRecipes[selectedRecipe].toString()
        database.child("recipes").child(key).get().addOnSuccessListener {
            val recipe = it.getValue(Food::class.java)!!
            val id = Datasource.mapOfPatients[user.info.name].toString()
            val day = Datasource.recipeDaySelected
            val typeOfFood = Datasource.foodSelected
            val currentFoodDay: FoodDayMenu = user.diets.days[day]!!
            currentFoodDay.foods.set(typeOfFood, recipe)
            currentFoodDay.day=day
            currentFoodDay.imageUri=recipe.imageResourceId
            user.diets.days[day] = currentFoodDay
            try{
                database.child("users_diets").child(id).child("days").child(day).setValue(currentFoodDay)
                    .addOnSuccessListener {
                        Datasource.setCurrentUser(user)
                        Toast.makeText(activity, "Receta adjuntada correctamente", Toast.LENGTH_LONG).show();
                        findNavController().navigate(R.id.action_assignRecipeFragment_to_dayRecipeFragment)
                    }
            }catch (e : Exception){
                Log.d("Exception",e.toString())
                Toast.makeText(activity, "Receta no pudo ser adjuntada", Toast.LENGTH_LONG).show();
            }
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }
}