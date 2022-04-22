package com.nutrikares.nutrideskapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.data.models.Stats
import com.nutrikares.nutrideskapp.databinding.FragmentAnalyticsBinding
import com.nutrikares.nutrideskapp.utils.ChartMarker

class FragmentAnalytics : Fragment() {

    private var _binding: FragmentAnalyticsBinding? = null
    private val binding get() = _binding!!

    private lateinit var userStats: Stats

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userStats = Datasource.getUserInfo().stats
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAnalyticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Bindings
        binding.imcDisplay.text = resources.getString(R.string.imc_placeholder, userStats.imc)
        binding.waistDisplay.text = resources.getString(R.string.measure, userStats.waist)
        binding.hipDisplay.text = resources.getString(R.string.measure, userStats.hip)
        binding.absDisplay.text = resources.getString(R.string.measure, userStats.abs)

        // Setting data
        val chartColor = ContextCompat.getColor(requireContext(), R.color.teal_200)
        val chartFill = ContextCompat.getDrawable(requireContext(), R.drawable.gradient_chart)
        val entries = userStats.relative.mapIndexed { idx, value -> Entry(idx.toFloat(), value.toFloat()) }

        // Line
        val lineDataset = LineDataSet(entries.toList(), "Peso (kg)")
        lineDataset.apply {
            color = chartColor
            lineWidth = 3f
            mode = LineDataSet.Mode.CUBIC_BEZIER
            setDrawCircles(false)
            setDrawValues(false)
            setDrawFilled(true)
            fillDrawable = chartFill
        }

        // Chart
        val chartData = LineData(lineDataset)
        val chart = binding.progressChart
        chart.apply {
            data = chartData
            description.text = ""
            marker = ChartMarker(context, R.string.weight_relative_card_label)
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