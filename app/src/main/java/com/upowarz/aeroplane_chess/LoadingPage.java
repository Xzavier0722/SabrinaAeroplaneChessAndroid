package com.upowarz.aeroplane_chess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoadingPage extends AppCompatActivity {
   private LinearLayout mlinearLayoutLoading;
   private ImageView loadingView;
   private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_page);

    }

    private void createLoadingView(){
        mlinearLayoutLoading  = new LinearLayout(mContext);
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayoutParams.gravity = Gravity.CENTER;
        mlinearLayoutLoading.setOrientation(LinearLayout.VERTICAL);
        loadingView = new ImageView(mContext);
        loadingView.setImageResource(R.drawable.loading);
        AnimationDrawable animationDrawable = (AnimationDrawable) loadingView.getDrawable();
        animationDrawable.start();
        mlinearLayoutLoading.addView(loadingView,linearLayoutParams);
        TextView textView = new TextView(mContext);
        textView.setText("Loading...");
        mlinearLayoutLoading.addView(textView,linearLayoutParams);
        mlinearLayoutLoading.setVisibility(View.GONE);
    }
}