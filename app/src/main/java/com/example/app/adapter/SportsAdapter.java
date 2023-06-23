package com.example.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.bean.Sports;

import java.lang.reflect.Field;
import java.util.List;

public class SportsAdapter extends RecyclerView.Adapter<SportsAdapter.MyHolder>{
    private View view;
    private Context context;
    private List<Sports> sportsList;
    private OnItemClickListener myOnItemClickListener;

    public interface OnItemClickListener {
        void onButtonClicked(View view, String id, ImageButton collectBtn, ImageButton collectedBtn);

        void onCollectedButton(View view, String id, ImageButton collectBtn, ImageButton collectedBtn);

    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.myOnItemClickListener = clickListener;
    }


    public SportsAdapter(Context context, List<Sports> sportsList) {
        this.context = context;
        this.sportsList = sportsList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.item_list_sports, parent, false);
        MyHolder myHolder= new MyHolder(view,myOnItemClickListener);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.sportsName.setText(sportsList.get(position).getSportsName());
        holder.sportsGym.setText(sportsList.get(position).getSportsGym());
        holder.sportsDate.setText(sportsList.get(position).getSportsDate());
        String path = sportsList.get(position).getPath();
        int drawableId = context.getResources().getIdentifier(path, "drawable", context.getPackageName());
        holder.sportsIcon.setImageDrawable(context.getDrawable(drawableId));
//        holder.sportsIcon.
        if (sportsList.get(position).getIfSelect()) {
            holder.collectBtn.setVisibility(View.GONE);
            holder.collectedBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return sportsList.size();
    }

    public int getItemViewType(int position) {
        // 给每个ItemView指定不同的类型，这样在RecyclerView看来，这些ItemView全是不同的，不能复用
        return position;
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        public TextView sportsName;
        public TextView sportsGym;
        public TextView sportsDate;
        private ImageButton collectBtn;
        private ImageButton collectedBtn;
        private ImageView sportsIcon;
        private Resources res;
        public MyHolder(@NonNull View itemView, final OnItemClickListener onClickListener) {
            super(itemView);
            sportsName = (TextView) itemView.findViewById(R.id.SportsName);
            sportsGym = (TextView) itemView.findViewById(R.id.SportsGym);
            sportsDate = (TextView) itemView.findViewById(R.id.SportsDate);
            collectBtn = (ImageButton) itemView.findViewById(R.id.collectButton);
            collectedBtn = (ImageButton) itemView.findViewById(R.id.collectedButton);
            sportsIcon = (ImageView) itemView.findViewById(R.id.sportsIcon);
            collectBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickListener != null) {
                        int position = getLayoutPosition();
                        boolean flag = false;
                        //确保position值有效
                        if (position != RecyclerView.NO_POSITION) {
                            onClickListener.onButtonClicked(view,sportsList.get(position).getId(),collectBtn,collectedBtn);
                        }
                    }
                }
            });

            collectedBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickListener != null) {
                        int position = getLayoutPosition();
                        boolean flag = false;
                        //确保position值有效
                        if (position != RecyclerView.NO_POSITION) {
                            onClickListener.onCollectedButton(view,sportsList.get(position).getId(),collectBtn,collectedBtn);
                        }
                    }
                }
            });
        }
    }
}
