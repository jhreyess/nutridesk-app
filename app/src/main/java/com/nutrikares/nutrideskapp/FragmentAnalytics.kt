package com.nutrikares.nutrideskapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.data.models.Stats
import com.nutrikares.nutrideskapp.databinding.FragmentAnalyticsBinding
import com.nutrikares.nutrideskapp.databinding.FragmentHomeBinding

class FragmentAnalytics : Fragment() {

    private var _binding: FragmentAnalyticsBinding? = null
    private val binding get() = _binding!!

    private lateinit var userStats: Stats

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userStats = Datasource.user.stats
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}