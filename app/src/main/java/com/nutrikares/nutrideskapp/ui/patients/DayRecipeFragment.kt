package com.nutrikares.nutrideskapp.ui.patients

import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nutrikares.nutrideskapp.R
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.databinding.FragmentDayRecipeBinding
import com.nutrikares.nutrideskapp.databinding.FragmentRecipeDaysBinding

class DayRecipeFragment : Fragment() {
    private var _binding: FragmentDayRecipeBinding? = null
    private val binding get() = _binding!!
    companion object{
        var Day : String? = "Domingo"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDayRecipeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.dayNameTextView.text = Day
        val foodDay = Datasource.getCurrentUser().diets.days[Datasource.recipeDaySelected]
        val breakfast = foodDay?.foods?.get("breakfast")?.name
        val meal = foodDay?.foods?.get("meal")?.name
        val dinner = foodDay?.foods?.get("dinner")?.name
        val snack1 = foodDay?.foods?.get("snack1")?.name
        val snack2 = foodDay?.foods?.get("snack2")?.name

        binding.breakfastRecipeTextView.text = if(breakfast.equals("")) resources.getString(R.string.noAssigned) else breakfast
        binding.mealRecipeTextView.text = if(meal.equals("")) resources.getString(R.string.noAssigned) else meal
        binding.dinnerRecipeTextView.text = if(dinner.equals("")) resources.getString(R.string.noAssigned) else dinner
        binding.firstSnackRecipeTextView.text = if(snack1.equals("")) resources.getString(R.string.noAssigned) else snack1
        binding.secondSnackRecipeTextView.text = if(snack2.equals("")) resources.getString(R.string.noAssigned) else snack2


        binding.breakfastRecipeCard.setOnClickListener{
            Datasource.foodSelected = "breakfast"
            findNavController().navigate(R.id.action_dayRecipeFragment_to_assignRecipeFragment)
        }
        binding.mealRecipeCard.setOnClickListener{
            Datasource.foodSelected = "meal"
            findNavController().navigate(R.id.action_dayRecipeFragment_to_assignRecipeFragment)
        }
        binding.dinnerRecipeCard.setOnClickListener{
            Datasource.foodSelected = "dinner"
            findNavController().navigate(R.id.action_dayRecipeFragment_to_assignRecipeFragment)
        }
        binding.firstSnackRecipeCard.setOnClickListener{
            Datasource.foodSelected = "snack1"
            findNavController().navigate(R.id.action_dayRecipeFragment_to_assignRecipeFragment)
        }
        binding.secondSnackRecipeCard.setOnClickListener{
            Datasource.foodSelected = "snack2"
            findNavController().navigate(R.id.action_dayRecipeFragment_to_assignRecipeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}