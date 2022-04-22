package com.nutrikares.nutrideskapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.databinding.FragmentHomeBinding
import com.nutrikares.nutrideskapp.utils.Calendar
import com.nutrikares.nutrideskapp.utils.ChartMarker

class FragmentHome : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userStats = Datasource.getUserInfo().stats
        
        // Bindings
        binding.weightDisplay.text = resources.getString(R.string.weight_placeholder, userStats.weight)
        binding.imcDisplay.text = resources.getString(R.string.imc_placeholder, userStats.imc)
        binding.todaysRoutine.setOnClickListener {
            val action = FragmentHomeDirections.actionFragmentHomeToFragmentExerciseHome()
            findNavController().navigate(action)
        }
        binding.todaysMenu.setOnClickListener {
            val weekDay = Calendar().getDate("es")
            val action = FragmentHomeDirections.actionFragmentHomeToFragmentFoodDailyHome(dayIndex = weekDay)
            findNavController().navigate(action)
        }

        // Setting data
        val chartColor = ContextCompat.getColor(requireContext(), R.color.teal_200)
        val chartFill = ContextCompat.getDrawable(requireContext(), R.drawable.gradient_chart)
        val entries = userStats.progress.mapIndexed { idx, value -> Entry(idx.toFloat(), value.toFloat()) }

        // Line
        val lineDataset = LineDataSet(entries, "Peso (kg)")
        lineDataset.apply {
            color = chartColor
            lineWidth = 3f
            mode = LineDataSet.Mode.CUBIC_BEZIER
            setDrawValues(false)
            setDrawCircles(false)
            setDrawFilled(true)
            fillDrawable = chartFill
        }

        // Chart
        val chartData = LineData(lineDataset)
        val chart = binding.progressChart
        chart.apply {
            data = chartData
            description.text = ""
            marker = ChartMarker(context, R.string.weight_card_label)
            setPinchZoom(false)
            minOffset = 0f
            axisLeft.isEnabled = false
            axisRight.isEnabled = false
            xAxis.isEnabled = false
            legend.isEnabled = false
            invalidate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}