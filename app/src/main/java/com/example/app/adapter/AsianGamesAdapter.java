package com.example.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.bean.AsianGames;
import com.github.mikephil.charting.charts.HorizontalBarChart;

import java.util.List;

public class AsianGamesAdapter extends RecyclerView.Adapter<AsianGamesAdapter.MyHolder>{
    private View view;
    private Context context;
    private List<AsianGames> asianGamesList;
    private OnItemClickListener myOnItemClickListener;

//    private ButtonInterface buttonInterface;


    public interface OnItemClickListener {
        void onButtonClicked(View view, String session, TextView figure, ImageButton lookButton1, ImageButton lookButton2, HorizontalBarChart barChart, LinearLayout medalsCharts);

        void onButtonClickedAgain(View view, ImageButton lookButton1, ImageButton lookButton2, LinearLayout medalsCharts);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.myOnItemClickListener = clickListener;
    }

    public AsianGamesAdapter(Context context, List<AsianGames> asianGamesList ) {
        this.context = context;
        this.asianGamesList = asianGamesList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.item_list_asiangames, parent, false);
        MyHolder myHolder= new MyHolder(view, myOnItemClickListener);

        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.asianGamesTitle.setText(asianGamesList.get(position).getAsianGamesTitle());
    }



    @Override
    public int getItemCount() {
        return asianGamesList.size();
    }

    public int getItemViewType(int position) {
        // 给每个ItemView指定不同的类型，这样在RecyclerView看来，这些ItemView全是不同的，不能复用
        return position;
    }
    public class MyHolder extends RecyclerView.ViewHolder{
        public TextView asianGamesTitle;
        public TextView figure;
        public ImageButton lookBtn1,lookBtn2;
        private HorizontalBarChart barChart;
        private LinearLayout medalsCharts;
        public MyHolder(@NonNull View itemView,final OnItemClickListener onClickListener) {
            super(itemView);
            asianGamesTitle = (TextView) itemView.findViewById(R.id.AsianGameTitle);
            figure = (TextView) itemView.findViewById(R.id.figure);
            lookBtn1 = (ImageButton) itemView.findViewById(R.id.LookButton);
            lookBtn2 = (ImageButton) itemView.findViewById(R.id.LookButton2);
            barChart = (HorizontalBarChart) itemView.findViewById(R.id.barCharts);
            medalsCharts = (LinearLayout) itemView.findViewById(R.id.medalsCharts);

            lookBtn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickListener != null) {
                        int position = getLayoutPosition();
                        //确保position值有效
                        if (position != RecyclerView.NO_POSITION) {
                            onClickListener.onButtonClicked(view,asianGamesList.get(position).getSession(),figure,lookBtn1,lookBtn2,barChart,medalsCharts);
                        }
                    }
                }
            });

            lookBtn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickListener != null) {
                        int position = getLayoutPosition();
                        //确保position值有效
                        if (position != RecyclerView.NO_POSITION) {
                            onClickListener.onButtonClickedAgain(view,lookBtn1,lookBtn2,medalsCharts);
                        }
                    }
                }
            });
        }
    }
}
