package com.nutrikares.nutrideskapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.nutrikares.nutrideskapp.adapters.FoodWeekAdapter
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.databinding.FragmentFoodBinding
import com.nutrikares.nutrideskapp.utils.Calendar

class FragmentFood : Fragment() {

    private var _binding: FragmentFoodBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFoodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = Datasource.getUserDiets()
        val days = Calendar.getWeekStartEnd()

        // Bindings
        binding.fragmentLabel.text = resources.getString(R.string.food_toolbar, days.first, days.second)

        // Adapter with custom layout
        val customLayoutManager = GridLayoutManager(activity, 2)
        customLayoutManager.spanSizeLookup = object: GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                return if (position == 0) 2 else 1
            }
        }

        binding.weekMenu.layoutManager = customLayoutManager
        binding.weekMenu.adapter = FoodWeekAdapter(this, data.days.values.toList())
        binding.weekMenu.setHasFixedSize(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}