package com.nomad.audit5s.Fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.nomad.audit5s.MyAxisValueFormatter;
import com.nomad.audit5s.MyValueFormatter;
import com.nomad.audit5s.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentBarrasApiladas extends Fragment   {

    private BarChart mChart;
    private SeekBar mSeekBarX, mSeekBarY;
    private TextView tvX, tvY;

    public static final String  PUNJTAJE1="PUNTAJE1";
    public static final String  PUNJTAJE2="PUNTAJE2";
    public static final String  PUNJTAJE3="PUNTAJE3";
    public static final String  PROMEDIO3S="PROMEDIO3S";

    private Double punt1;
    private Double punt2;
    private Double punt3;

    private Double puntpro;


    public FragmentBarrasApiladas()  {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_barras_apiladas, container, false);
        Bundle bundle = getArguments();
        punt1=bundle.getDouble(PUNJTAJE1);
        punt2=bundle.getDouble(PUNJTAJE2);
        punt3=bundle.getDouble(PUNJTAJE3);
        puntpro=bundle.getDouble(PROMEDIO3S);




        mChart = (BarChart) view.findViewById(R.id.chart1);
        mChart.getDescription().setEnabled(false);



        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(40);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(true);
        mChart.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.marfil));
        mChart.setGridBackgroundColor(ContextCompat.getColor(getContext(),R.color.marfil));
        mChart.setDrawBarShadow(false);


        mChart.setHighlightFullBarEnabled(false);

        // change the position of the y-labels
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setValueFormatter(new MyAxisValueFormatter());
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        mChart.getAxisRight().setEnabled(false);
        leftAxis.setDrawLabels(false);

       XAxis xaxis=mChart.getXAxis();
        xaxis.setDrawAxisLine(false);
        xaxis.setDrawLabels(false);
       // xLabels.setPosition(XAxis.XAxisPosition.TOP);

       // mChart.setDrawXLabels(false);
        // mChart.setDrawYLabels(false);



        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setFormToTextSpace(4f);
        l.setXEntrySpace(6f);

        Float punto1= Float.parseFloat(punt1.toString());
        Float punto2= Float.parseFloat(punt2.toString());
        Float punto3= Float.parseFloat(punt3.toString());
        Float promedio= Float.parseFloat(puntpro.toString());



        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();
        yVals1.add(new BarEntry(
                1,
                new float[]{promedio},
                getResources().getDrawable(R.drawable.radar_marker)));

        yVals2.add(new BarEntry(
                2,
                new float[]{0.5f,0.3f,0.2f},
                getResources().getDrawable(R.drawable.radar_marker)));






        BarDataSet set1;
        BarDataSet set2;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        }
        else {
            set1 = new BarDataSet(yVals1, "");
            set1.setDrawIcons(false);

            if (puntpro<=0.5f){
                set1.setColor(ContextCompat.getColor(getContext(), R.color.semaRojo));
            }
            else{
                if (puntpro<0.8f){
                    set1.setColor(ContextCompat.getColor(getContext(), R.color.semaAmarillo));
                }
                else{
                    set1.setColor(ContextCompat.getColor(getContext(), R.color.semaVerde));
                }
            }
            set1.setLabel("Audit result");
            set1.setStackLabels(new String[]{"Audit result"});



            set2 = new BarDataSet(yVals2,"");
            set2.setDrawIcons(false);
            set2.setColors(ContextCompat.getColor(getContext(), R.color.semaRojo),ContextCompat.getColor(getContext(), R.color.semaAmarillo),ContextCompat.getColor(getContext(), R.color.semaVerde));
            set2.setStackLabels(new String[]{"Bad", "Average", "Good"});
            set2.setDrawValues(false);





            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            dataSets.add(set2);


            BarData data = new BarData(dataSets);
            data.setValueFormatter(new MyValueFormatter());
            data.setValueTextColor(Color.BLACK);
            data.setValueTextSize(18f);

            mChart.setData(data);
        }

        Legend legend=mChart.getLegend();
        legend.setTextSize(16f);
        mChart.setDrawValueAboveBar(false);
        mChart.setFitBars(true);
        mChart.invalidate();

        // mChart.setDrawLegend(false);
        return view;
    }








    private int[] getColors() {
        int stacksize = 3;

        // have as many colors as stack-values per entry
        int[] colors = new int[stacksize];

        for (int i = 0; i < colors.length; i++) {
            colors[i] = ColorTemplate.MATERIAL_COLORS[i];
        }

        return colors;
    }














}
