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
import com.nutrikares.nutrideskapp.databinding.FragmentCreateRecipeInformationBinding

class CreateRecipeInformationFragment : Fragment() {

    private var _binding: FragmentCreateRecipeInformationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateRecipeInformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(Datasource.getClickOnRecipe()) {
            val recipe = Datasource.getCurrentRecipe()
            binding.recipeCaloriesEditText.setText(recipe.info.calories.toString())
            binding.recipeCarbsEditText.setText(recipe.info.carbs.toString())
            binding.recipeFatsEditText.setText(recipe.info.fats.toString())
            binding.recipeProteinEditText.setText(recipe.info.protein.toString())
        }

        binding.acceptButton.setOnClickListener {
            if(checkFields()){
                attachData()
                if(Datasource.getClickOnRecipe()){
                    if(Datasource.updateRecipe()){
                        Toast.makeText(this.context, "Receta modificada exitosamente", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(this.context, "La receta no pudo ser modificada", Toast.LENGTH_LONG).show();
                    }
                }else{
                    if(Datasource.addRecipe()){
                        Toast.makeText(this.context, "Receta agregada exitosamente", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(this.context, "La receta no pudo ser agregada", Toast.LENGTH_LONG).show();
                    }
                }
                findNavController().navigate(R.id.action_createRecipeInformationFragment_to_nav_recipes)
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
        return !(binding.recipeCaloriesEditText.text.isEmpty() || binding.recipeCarbsEditText.text.isEmpty() ||
                binding.recipeFatsEditText.text.isEmpty() || binding.recipeProteinEditText.text.isEmpty())
    }

    fun attachData(){
        Datasource.newRecipe.info.calories = Integer.parseInt(binding.recipeCaloriesEditText.text.toString())
        Datasource.newRecipe.info.carbs = Integer.parseInt(binding.recipeCarbsEditText.text.toString())
        Datasource.newRecipe.info.fats = Integer.parseInt(binding.recipeFatsEditText.text.toString())
        Datasource.newRecipe.info.protein = Integer.parseInt(binding.recipeProteinEditText.text.toString())
        Log.v("Data-final",Datasource.newRecipe.toString())
    }

}