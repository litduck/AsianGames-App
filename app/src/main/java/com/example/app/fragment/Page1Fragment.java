package com.example.app.fragment;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.app.R;

public class Page1Fragment extends Fragment {
    private LinearLayout linearLayout1, linearLayout2, linearLayout3;
    private ScrollView scrollView;
    private Button smoothButton1,smoothButton2,smoothButton3;
    private ImageView smallImage, imageView;
    private TextView banner;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page1, container, false);

        linearLayout1 = (LinearLayout) view.findViewById(R.id.linearLayout1);
        linearLayout2 = (LinearLayout) view.findViewById(R.id.linearLayout2);
        linearLayout3 = (LinearLayout) view.findViewById(R.id.linearLayout3);
        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        smoothButton1 = (Button) view.findViewById(R.id.smoothButton1);
        smoothButton2 = (Button) view.findViewById(R.id.smoothButton2);
        smoothButton3 = (Button) view.findViewById(R.id.smoothButton3);
        smallImage = (ImageView) view.findViewById(R.id.smallImage);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        banner = (TextView) view.findViewById(R.id.banner);

        smoothButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                smooth(linearLayout1);
            }
        });

        smoothButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                smooth(linearLayout2);
            }
        });

        smoothButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                smooth(linearLayout3);
            }
        });

        smallImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap0 = ((BitmapDrawable)smallImage.getDrawable()).getBitmap();
                bigImageLoader(bitmap0);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap0 = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                bigImageLoader(bitmap0);
            }
        });

        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_UP);
                    }
                });
            }
        });


        return view;
    }

    private void smooth(LinearLayout layout){
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0, layout.getTop());
            }
        });
    }

    private void bigImageLoader(Bitmap bitmap){
        final Dialog dialog = new Dialog(getActivity());
        ImageView image = new ImageView(getContext());
        image.setImageBitmap(bitmap);
        dialog.setContentView(image);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dialog.cancel();
            }
        });
    }

}
