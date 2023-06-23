package com.example.app.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.adapter.AsianGamesAdapter;
import com.example.app.bean.AsianGames;
import com.example.app.bean.Medals;
import com.example.app.bean.Sports;
import com.example.app.util.GetSQLite;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Page2Fragment extends Fragment {
    private List<AsianGames> asianGamesList;
    private RecyclerView asianGamesRecyclerView;
    private List<Medals> medalsList;
    private List<Sports> sportsList;
    private TextView fig;
    private String se = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page2, container, false);

        asianGamesRecyclerView = (RecyclerView)view.findViewById(R.id.AsianGamesrecyclerList);

        GetSQLite getSQLite=new GetSQLite();
        asianGamesList = getSQLite.setAsianGamesList(this.getActivity());
        AsianGamesAdapter asianGamesAdapter = new AsianGamesAdapter(this.getActivity(), asianGamesList);
        LinearLayoutManager manager = new LinearLayoutManager(this.getActivity());
        //设置布局
        asianGamesRecyclerView.setLayoutManager(manager);
        //设置动画
        asianGamesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        asianGamesRecyclerView.setAdapter(asianGamesAdapter);

        asianGamesAdapter.setOnItemClickListener(new AsianGamesAdapter.OnItemClickListener() {
            @Override
            public void onButtonClicked(View view, String session, TextView figure, ImageButton lookButton1, ImageButton lookButton2, HorizontalBarChart barChart, LinearLayout medalsCharts) {
                lookButton1.setVisibility(View.GONE);
                lookButton2.setVisibility(View.VISIBLE);
                medalsCharts.setVisibility(View.VISIBLE);
                getMedals(session,figure,barChart);
            }

            @Override
            public void onButtonClickedAgain(View view, ImageButton lookButton1, ImageButton lookButton2, LinearLayout medalsCharts) {
                lookButton1.setVisibility(View.VISIBLE);
                lookButton2.setVisibility(View.GONE);
                medalsCharts.setVisibility(View.GONE);
            }
        });

        return view;
    }
    private void getMedals(String session, TextView figure,HorizontalBarChart barChart){
        List<IBarDataSet> sets = new ArrayList<>();

        GetSQLite getSQLite=new GetSQLite();
        medalsList = getSQLite.setMedalsList(this.getActivity(),session);
        Collections.sort(medalsList, new Comparator<Medals>() {
            @Override
            public int compare(Medals p1, Medals p2) {
                return p1.getTotal() - p2.getTotal();
            }
        });

        barChart.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams params = barChart.getLayoutParams();
                //设置高度值
                params.height = medalsList.size()*80;
                //使设置好的布局参数应用到控件
                barChart.setLayoutParams(params);
            }
        });
        figure.setText(session+"");

        ArrayList<BarEntry> visitors = new ArrayList<>();
        for(int i=0; i<medalsList.size(); i++){
            float total = medalsList.get(i).getTotal();
            float golden = medalsList.get(i).getGolden();
            float silver = medalsList.get(i).getSilver();
            float bronze = medalsList.get(i).getBronze();
            visitors.add(new BarEntry(i, new float[]{bronze,silver,golden}));
        }
        BarDataSet barDataSet1 = new BarDataSet(visitors,"");
//        barDataSet1.setValueTextSize(15);

        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#FFA2A2"));
        colors.add(Color.parseColor("#FF8686"));
        colors.add(Color.parseColor("#FF6363"));
        barDataSet1.setColors(colors);

        String[] label = new String[]{"Bronze","Silver","Golden"};

        barDataSet1.setStackLabels(label);
        barDataSet1.setDrawValues(false);

        BarData barData = new BarData(barDataSet1);
        barData.setBarWidth(0.8f);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("金牌榜");
        barChart.animateX(2000);
        barChart.setExtraLeftOffset(60);
        setAxis(medalsList,barChart);
    }

    private void setAxis(List<Medals> medalsList,HorizontalBarChart barChart){
        // 设置x轴
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);  // 设置x轴显示在下方，默认在上方
        xAxis.setDrawGridLines(false); // 将此设置为true，绘制该轴的网格线。
        xAxis.setDrawAxisLine(false);
        xAxis.setLabelCount(medalsList.size());  // 设置x轴上的标签个数
        xAxis.setTextSize(12f); // x轴上标签的大小
//        xAxis.mLabelWidth = 150;
        // 设置x轴显示的值的格式
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if ((int) value < medalsList.size()) {
                    return medalsList.get((int) value).getCountry();
                } else {
                    return "";
                }
            }
        });
        xAxis.setYOffset(5); // 设置标签对x轴的偏移量，垂直方向

        YAxis yAxis_left = barChart.getAxisLeft();
        YAxis yAxis_right = barChart.getAxisRight();
        yAxis_left.setEnabled(false);
        yAxis_right.setEnabled(false);
    }

}
