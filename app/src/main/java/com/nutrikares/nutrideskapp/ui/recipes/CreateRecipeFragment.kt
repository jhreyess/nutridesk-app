package com.nutrikares.nutrideskapp.ui.recipes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.nutrikares.nutrideskapp.R
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.databinding.FragmentCreateRecipeBinding

class CreateRecipeFragment : Fragment() {

    private var _binding: FragmentCreateRecipeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(Datasource.getClickOnRecipe()){
            binding.idRecipeEditText.isFocusable = false
            val recipe = Datasource.getCurrentRecipe()
            binding.idRecipeEditText.setText(Datasource.mapOfRecipes[recipe.name])
            binding.timeRecipeEditText.setText(recipe.time.toString())
            binding.nameRecipeEditText.setText(recipe.name)
            binding.descRecipeEditText.setText(recipe.description)
            when(recipe.type){
                "Desayuno" -> binding.radioGroup1.check(binding.breakfastRadioButton.id)
                "Comida" -> binding.radioGroup1.check(binding.mealRadioButton.id)
                "Cena" -> binding.radioGroup1.check(binding.dinnerRadioButton.id)
                "Snack" -> binding.radioGroup1.check(binding.snackRadioButton.id)
                else -> binding.radioGroup1.check(binding.breakfastRadioButton.id)
            }
        }
        binding.acceptButton.setOnClickListener {
            if(checkFields()){
                attachData()
                findNavController().navigate(R.id.action_createRecipeFragment_to_createRecipePreparationFragment)
            }else{
                Toast.makeText(activity, "Faltan datos por llenar",Toast.LENGTH_LONG).show();
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun attachData(){
        Datasource.newRecipe.name = binding.nameRecipeEditText.text.toString()
        Datasource.newRecipeId = binding.idRecipeEditText.text.toString()
        Datasource.newRecipe.time = Integer.parseInt(binding.timeRecipeEditText.text.toString())
        Datasource.newRecipe.type = when(binding.radioGroup1.checkedRadioButtonId){
            binding.breakfastRadioButton.id ->  "Desayuno"
            binding.mealRadioButton.id -> "Comida"
            binding.dinnerRadioButton.id -> "Cena"
            binding.snackRadioButton.id -> "Snack"
            else -> "Desayuno"
        }
        Datasource.newRecipe.description = binding.descRecipeEditText.text.toString()
        Log.v("Data",Datasource.newRecipe.toString())
    }

    fun checkFields():Boolean{
        return !(binding.timeRecipeEditText.text.isEmpty() || binding.nameRecipeEditText.text.isEmpty()
                || binding.descRecipeEditText.text.isEmpty() || binding.idRecipeEditText.text.isEmpty())
    }

}