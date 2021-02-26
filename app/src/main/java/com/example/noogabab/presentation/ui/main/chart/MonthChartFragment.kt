package com.example.noogabab.presentation.ui.main.chart

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.get
import androidx.lifecycle.Observer
import com.example.noogabab.R
import com.example.noogabab.util.SharedGroup
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.fragment_month_chart.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MonthChartFragment : Fragment(R.layout.fragment_month_chart), View.OnClickListener {
    private var groupSize = 0
    private var xGroup: ArrayList<String> = ArrayList<String>()
    private lateinit var yBob: FloatArray
    private lateinit var ySnack: FloatArray
    private lateinit var sharedGroup: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedGroup = requireActivity().getSharedPreferences(SharedGroup.NAME, Context.MODE_PRIVATE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setValues()
        load()
    }

    private fun load() {
        btn_select_month_date.setOnClickListener(this)
        btn_month_rank_bob.setOnClickListener(this)
        btn_month_rank_snack.setOnClickListener(this)
    }

    private fun getFirst(size: Int, position: Int) {
        for (i in 0 until size) linear_month_rank[i].visibility = View.INVISIBLE
        linear_month_rank[position].visibility = View.VISIBLE
    }

    // 서버에서 가져온 데이터를 셋팅
    private fun setValues() {
        xGroup.add("나")
        xGroup.add("엄마")
        xGroup.add("아빠")
        xGroup.add("누나")
        groupSize = xGroup.size
        yBob = floatArrayOf(2.0f, 6f, 7.8f, 3.4f)
        ySnack = floatArrayOf(1.0f, 7f, 3.8f, 8.4f)

        setBarChartValues(xGroup, yBob, ySnack)

        // 가족 구성원에 맞춰서 INVISIBLE 뷰 셋팅
        for (i in 0 until groupSize) linear_month_rank.addView(createRankImage())
    }

    private fun createRankImage(): ImageView {
        val rankImage = ImageView(context)
        rankImage.visibility = View.INVISIBLE
        rankImage.setImageResource(R.drawable.ic_first_xxxhd)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.weight = 1f
        rankImage.layoutParams = params
        return rankImage
    }

    private fun setBarChartValues(
        xValues: ArrayList<String>,
        yAxis1: FloatArray,
        yAxis2: FloatArray
    ) {
        // bar entries
        val barEntries1 = ArrayList<BarEntry>()
        val barEntries2 = ArrayList<BarEntry>()
        for (i in yAxis1.indices) barEntries1.add(BarEntry(yAxis1[i], i))
        for (i in yAxis2.indices) barEntries2.add(BarEntry(yAxis2[i], i))

        // barData set
        val barDataSet1 = BarDataSet(barEntries1, "밥")
        val barDataSet2 = BarDataSet(barEntries2, "간식")
        barDataSet1.color = resources.getColor(R.color.color_aa5900)
        barDataSet2.color = resources.getColor(R.color.color_ffb254)

        val finalBarDataSet = ArrayList<BarDataSet>()
        finalBarDataSet.add(barDataSet1)
        finalBarDataSet.add(barDataSet2)

        // make a bar data
        val barData = BarData(xValues, finalBarDataSet as List<IBarDataSet>)
        barData.setValueFormatter(ChartValueFormatter())
        chart_month_bar.apply {
            data = barData
            setBackgroundColor(Color.WHITE)
            animateXY(3000, 3000)
            setDrawGridBackground(false)
            setTouchEnabled(false)
            setDescription("")
            extraBottomOffset = 20f

            xAxis.apply {
                textSize = 14f
                position = XAxis.XAxisPosition.BOTTOM
            }
            axisLeft.apply {
                textSize = 10f
                setDrawGridLines(true)
                mAxisMinimum = 0f

            }
            axisRight.apply {
                isEnabled = false
            }
            legend.apply {
                isEnabled = false
            }
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            btn_month_rank_bob -> bobRankClick()
            btn_month_rank_snack -> snackRankClick()
            btn_select_month_date -> selectDate()
        }
    }

    private fun bobRankClick() {
        var position = 0
        var max = -1f
        for (i in yBob.indices) if (max < yBob[i]) {
            max = yBob[i];
            position = i
        }
        getFirst(groupSize, position)
    }

    private fun snackRankClick() {
        var position = 0
        var max = -1f
        for (i in ySnack.indices) if (max < ySnack[i]) {
            max = ySnack[i];
            position = i
        }
        getFirst(groupSize, position)
    }

    private fun selectDate() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("날짜를 선택하세요")
            .build()
        datePicker.show(requireActivity().supportFragmentManager, "DATE_PICKER")
        datePicker.addOnPositiveButtonClickListener {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val formatTime = sdf.format(Date(it))
            Toast.makeText(requireContext(), formatTime, Toast.LENGTH_LONG).show()
        }

    }
}
