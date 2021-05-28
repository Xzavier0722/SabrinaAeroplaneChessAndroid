package com.upowarz.aeroplane_chess;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsoluteLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import java.util.List;
import java.util.Map;

public class GameProcessPage extends AppCompatActivity {
    AbsoluteLayout mABLayout;
    RelativeLayout mRelayout;
    RelativeLayout.LayoutParams mReParams;
    Map<Integer,ImageButton> cheeses;
    List<AbsoluteLayout.LayoutParams> cheesesParams;
    Map<ImageButton,ViewGroup.LayoutParams> chessLocation;
    ImageButton mTestChess;
    ImageButton mTestChess2;
    int width;
    int height;
    int chessSize;
    int testX;
    int testY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_process_page);
        init();
        mABLayout.setLayoutParams(mReParams);
        mABLayout.setBackground(getResources().getDrawable(R.drawable.game_map));
        mRelayout.addView(mABLayout, mReParams);
        AbsoluteLayout.LayoutParams layoutParams=new AbsoluteLayout.LayoutParams(chessSize,chessSize,testX,testY);
        mTestChess.setBackground(getResources().getDrawable(R.drawable.blue));
        mABLayout.addView(mTestChess,layoutParams);

    }
    private void init(){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mABLayout=new AbsoluteLayout(this);
        mRelayout=(RelativeLayout)findViewById(R.id.ReLayout);
        width = metrics.widthPixels;
        height = metrics.heightPixels;
        /*
        chessSize=width/36*2;
        testX=width/36*3;
        testY=width/36*29;
        mReParams =new RelativeLayout.LayoutParams(width,width);
        chessSize=height/36*2;
        testX=height/36*3;
        testY=height/36*29;
        mReParams =new RelativeLayout.LayoutParams(height,height);
         */
        chessSize=height/36*2;
        testX=height/36*3;
        testY=height/36*29;
        mReParams =new RelativeLayout.LayoutParams(height,height);
        mTestChess=new ImageButton(this);
        mTestChess2=new ImageButton(this);

    }

    public void setChessLocation(AbsoluteLayout.LayoutParams params,int x,int y){
        params.x=x;
        params.y=y;
    }

    public int getChessLocationX(AbsoluteLayout.LayoutParams params){
        return  params.x;
    }

    public int getChessLocationY(AbsoluteLayout.LayoutParams params){
        return  params.y;
    }

    public void initChess(AbsoluteLayout.LayoutParams params, ImageButton imageButton){

    }


}