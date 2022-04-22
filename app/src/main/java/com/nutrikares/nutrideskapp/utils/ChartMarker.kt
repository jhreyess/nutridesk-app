package com.nutrikares.nutrideskapp.utils

import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.nutrikares.nutrideskapp.R

class ChartMarker constructor(
    context: Context,
    private val stringRes: Int
    ) : MarkerView(context, R.layout.chart_marker) {

    private var tvContent: TextView = findViewById(R.id.tvContent)

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    override fun refreshContent(e: Entry, highlight: Highlight?) {
        // set the entry-value as the display text
        val resources = context.resources
        tvContent.text = resources.getString(stringRes, e.y)
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF(-(width / 2f), -(height.toFloat() + 25f))
    }
}