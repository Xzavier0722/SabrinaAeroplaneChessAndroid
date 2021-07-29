package com.upowarz.aeroplanechess;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.Player;

import java.util.List;

public class GameEndPage extends AppCompatActivity {

    private List<Player> playerList;

    private LinearLayout linearLayoutGameEnd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_end_page);
        linearLayoutGameEnd = findViewById(R.id.llayoutGameEnd);
        playerList = CacheManager.get("rankList",List.class,null);
        TextView textView=new TextView(this);
        Typeface font=Typeface.createFromAsset(this.getAssets(),"iconfont.ttf");
        textView.setTypeface(font);
        textView.setText(R.string.champion);
        textView.setTextSize(40);
        textView.setTextColor(Color.YELLOW);
        textView.setGravity(Gravity.CENTER);
        addLayout(textView,1000,0,1);
        loadRank();
    }
    private void loadRank(){
        for(Player player:playerList){
            TextView textView = new TextView(this);
            if(playerList.get(0)==player){
                textView.setTextSize(40);
                textView.setGravity(Gravity.CENTER);
                textView.setText(player.getName());
                addLayout(textView,1000,0,1);
            }else{
                textView.setText(player.getName());
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(30);
                addLayout(textView,1000,0,1);
            }
        }

    }

    private void addLayout(View view, int w, int h, int weight){
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(w,h,weight);
        layoutParams.setMargins(5,0,5,5);
        linearLayoutGameEnd.setGravity(Gravity.CENTER);
        linearLayoutGameEnd.addView(view,layoutParams);
    }
}