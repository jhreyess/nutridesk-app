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
import com.nutrikares.nutrideskapp.databinding.FragmentCreateRecipePreparationBinding
import javax.sql.DataSource


class CreateRecipePreparationFragment : Fragment() {
    private var _binding: FragmentCreateRecipePreparationBinding? = null
    private val binding get() = _binding!!
    private var steps : MutableList<String> = mutableListOf()
    private var ingredients : MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateRecipePreparationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if(Datasource.getClickOnRecipe()) {
            val recipe = Datasource.getCurrentRecipe()
            ingredients=recipe.ingredients
            steps=recipe.steps
            binding.listOfIngredientsTextView.setText(convertListIntoString(ingredients))
            binding.listOfStepsTextView.setText(convertListIntoString(steps))
        }

        binding.addRecipeIngredientImageButton.setOnClickListener{
            addIngredient()
        }
        binding.addRecipeStepImageButton.setOnClickListener{
            addStep()
        }

        binding.acceptButton.setOnClickListener {
            if(checkFields()){
                attachData()
                findNavController().navigate(R.id.action_createRecipePreparationFragment_to_createRecipeInformationFragment)
            }else{
                Toast.makeText(this.context, "Faltan datos por llenar", Toast.LENGTH_LONG).show();
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun checkFields():Boolean{
        return !(binding.listOfIngredientsTextView.text.isEmpty() || binding.listOfStepsTextView.text.isEmpty())
    }

    fun convertListIntoString(lista : MutableList<String>) : String{
        var cadena = ""
        lista.forEach{
            cadena+=it+"\n"
        }
        return cadena
    }

    fun addIngredient(){
        if(!binding.recipeIngredientEditText.text.isEmpty()){
            val ingredient = binding.recipeIngredientEditText.text.toString()
            ingredients.add(ingredient)
            binding.listOfIngredientsTextView.setText(convertListIntoString(ingredients))
            binding.recipeIngredientEditText.setText("")
        }
    }
    fun addStep(){
        if(!binding.recipeStepEditText.text.isEmpty()){
            val step = binding.recipeStepEditText.text.toString()
            steps.add(step)
            binding.listOfStepsTextView.setText(convertListIntoString(steps))
            binding.recipeStepEditText.setText("")
        }
    }
    fun attachData(){
        Datasource.newRecipe.ingredients = ingredients
        Datasource.newRecipe.steps = steps
        Log.v("Data-2",Datasource.newRecipe.toString())
    }
}