package com.upowarz.aeroplanechess;

import androidx.appcompat.app.AppCompatActivity;

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
    private LinearLayout linearLayoutGameEnd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_end_page);
        loadRank();
    }

    private void loadRank(){
        linearLayoutGameEnd = findViewById(R.id.llayoutGameEnd);
        List<Player> playerList = CacheManager.get("rankList",List.class,null);
        for(Player player:playerList){
            TextView textView = new TextView(this);
            if(playerList.get(0)==player){
                Typeface font=Typeface.createFromAsset(this.getAssets(),"iconfont.ttf");
                textView.setTypeface(font);
                textView.setTextSize(70);
                textView.setGravity(Gravity.CENTER);
                textView.setText(R.string.champion+" "+player.getName());
                addLayout(textView,70,70,1);
            }else{
                textView.setText(player.getName());
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(50);
                addLayout(textView,50,50,2);
            }
        }

    }

    private void addLayout(View view, int w, int h, int weight){
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(w,h,weight);
        layoutParams.setMargins(5,5,5,5);
        linearLayoutGameEnd.setGravity(Gravity.CENTER);
        linearLayoutGameEnd.addView(view,layoutParams);
    }
}