package id.go.bpkp.mobilemapbpkp.konfigurasi.graphs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.Random;

import id.go.bpkp.mobilemapbpkp.R;

public class Graphs {

    public static PieChart createPieChart(Context context, ArrayList<String> stringDataArray, ArrayList<String> stringLabelArray, PieChart chartView) {
        chartView.setUsePercentValues(true);
        chartView.getDescription().setEnabled(false);
        chartView.setExtraOffsets(5, 10, 5, 5);

        chartView.setDragDecelerationFrictionCoef(0.95f);

        chartView.setDrawHoleEnabled(true);
        chartView.setHoleColor(context.getResources().getColor(R.color.transparent));

        chartView.setTransparentCircleColor(Color.WHITE);
        chartView.setTransparentCircleAlpha(110);

        chartView.setHoleRadius(25f);
        chartView.setTransparentCircleRadius(30f);

        chartView.setDrawCenterText(true);
        chartView.setCenterTextTypeface(Typeface.MONOSPACE);

        chartView.setRotationAngle(0);
        chartView.setRotationEnabled(true);
        chartView.setHighlightPerTapEnabled(true);

        setPieData(
                context,
                stringDataArray.size(),
                stringDataArray,
                stringLabelArray,
                chartView);

        chartView.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // chartView.spin(2000, 0, 360);

        Legend l = chartView.getLegend();
        l.setEnabled(false);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);

        chartView.setEntryLabelColor(context.getResources().getColor(R.color.blueBasicDark));
        chartView.setEntryLabelTextSize(12f);

        return chartView;
    }

    private static void setPieData(Context context, int count, ArrayList<String> stringDataArray, ArrayList stringLabelArray, PieChart chartView) {
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count; i++) {
            entries.add(new PieEntry(
                            (float) Integer.parseInt(stringDataArray.get(i)),
                            stringLabelArray.get(i).toString().toUpperCase()
                    )
            );
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(0f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors
        ArrayList<Integer> colors = new ArrayList<Integer>();
        Random rnd = new Random();
        for (int i = 0; i < count; i++) {
            colors.add(Color.argb(
                    255,
                    rnd.nextInt(256),
                    256 - (rnd.nextInt(200)),
                    256 - (rnd.nextInt(200))));
        }
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        //dataSet.setSelectionShift(0f);
        dataSet.setDrawValues(false);
//        dataSet.setValueLinePart1OffsetPercentage(80.f);
//        dataSet.setValueLinePart1Length(0.2f);
//        dataSet.setValueLinePart2Length(0.4f);
//        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(15f);
        data.setValueTextColor(context.getResources().getColor(R.color.blueBasicDark));
        data.setValueTypeface(Typeface.MONOSPACE);
        chartView.setData(data);

        // undo all highlights
        chartView.highlightValues(null);

        chartView.invalidate();
    }

    public static BarChart createBarChart(Context context, ArrayList<String> stringDataArray, ArrayList<String> stringLabelArray, BarChart chartView, String title) {
//        chartView.setOnChartValueSelectedListener(this);

        chartView.setDrawBarShadow(false);
        chartView.setDrawValueAboveBar(true);

        chartView.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chartView.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        chartView.setPinchZoom(false);

        chartView.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);
//
//        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(chartView);

        XAxis xAxis = chartView.getXAxis();

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        LabelAxisValueFormatter labelFormatter = new LabelAxisValueFormatter(stringLabelArray);
//        xAxis.setTypeface(mTfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(stringDataArray.size());
        xAxis.setValueFormatter(labelFormatter);

        IAxisValueFormatter custom = new MyAxisValueFormatter();

        YAxis leftAxis = chartView.getAxisLeft();
//        leftAxis.setTypeface(mTfLight);
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        Legend l = chartView.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        l.isLegendCustom();
        l.setEnabled(false);

        XYMarkerView mv = new XYMarkerView(context, custom, labelFormatter, title);
        mv.setChartView(chartView); // For bounds control
        chartView.setMarker(mv); // Set the marker to the chart

        chartView.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        setBarData(
                context,
                stringDataArray.size(),
                stringDataArray,
                chartView);

        return chartView;
    }

    private static void setBarData(Context context, int count, ArrayList<String> stringDataArray, BarChart chartView) {

        float start = 1f;

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < count; i++) {
            yVals1.add(new BarEntry(
                            (float) i,
                            (float) Integer.parseInt(stringDataArray.get(i))
                    )
            );
        }

        BarDataSet set1;

        if (chartView.getData() != null && chartView.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chartView.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            chartView.getData().notifyDataChanged();
            chartView.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "");
            set1.setDrawIcons(false);
            set1.setColors(ColorTemplate.MATERIAL_COLORS);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10);
            IValueFormatter valueFormatter = new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    String valueStr = (String) Integer.toString((int) value);
                    return valueStr;
                }
            };
            data.setValueFormatter(valueFormatter);
            data.setBarWidth(0.9f);
            chartView.setData(data);
        }
    }

}
