package com.nutrikares.nutrideskapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.nutrikares.nutrideskapp.adapters.FoodDayAdapter
import com.nutrikares.nutrideskapp.databinding.FragmentFoodDailyBinding

class FragmentFoodDaily : Fragment() {

    companion object{
        const val DAY = "dayIndex"
        const val TODAY = "todayDate"
    }

    object Days {
        const val MONDAY = 1
        const val TUESDAY = 2
        const val WEDNESDAY = 3
        const val THURSDAY = 4
        const val FRIDAY = 5
        const val SATURDAY = 6
        const val SUNDAY = 7
    }

    private val date = {x: String -> when(x){
            "Lunes" -> Days.MONDAY
            "Martes" -> Days.TUESDAY
            "Miércoles" -> Days.WEDNESDAY
            "Jueves" -> Days.THURSDAY
            "Viernes" -> Days.FRIDAY
            "Sábado" -> Days.SATURDAY
            "Domingo" -> Days.SUNDAY
            else -> 0
    }}

    private var _binding: FragmentFoodDailyBinding? = null
    private val binding get() = _binding!!

    private lateinit var menuDay: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve the DATE from the Fragment arguments
        arguments?.let {
            menuDay = it.getString(DAY).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFoodDailyBinding.inflate(inflater, container, false)

        // Bindings
        binding.fragmentLabel.text = resources.getString(R.string.menu_date, date(menuDay), menuDay)
        binding.backButton.setOnClickListener { findNavController().popBackStack() }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Adapter with custom layout
        val customLayoutManager = GridLayoutManager(activity, 2)
        customLayoutManager.spanSizeLookup = object: GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                return if (position == 0) 2 else 1
            }
        }
        binding.dailyMenu.layoutManager = customLayoutManager
        binding.dailyMenu.adapter = FoodDayAdapter(this, menuDay)
        binding.dailyMenu.setHasFixedSize(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}