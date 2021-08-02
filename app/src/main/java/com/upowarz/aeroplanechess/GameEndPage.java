package com.upowarz.aeroplanechess;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
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
        linearLayoutGameEnd.setGravity(Gravity.CENTER);

        TextView textView=new TextView(this);
        Typeface font=Typeface.createFromAsset(this.getAssets(),"iconfont.ttf");
        textView.setTypeface(font);
        textView.setText(R.string.champion);
        textView.setTextSize(40);
        textView.setTextColor(Color.YELLOW);
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayoutGameEnd.addView(textView);

        loadRank();
    }
    private void loadRank(){
        for(Player player:playerList){
            TextView textView = new TextView(this);
            if(playerList.get(0)==player){
                textView.setGravity(Gravity.CENTER);
                textView.setText(player.getName());
                textView.setTextSize(70);
                textView.setTextColor(Color.YELLOW);
                textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                linearLayoutGameEnd.addView(textView);
            }else{
                textView.setText(player.getName());
                textView.setTextSize(50);
                textView.setGravity(Gravity.CENTER);
                textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                linearLayoutGameEnd.addView(textView);
            }
        }

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK){
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure to return the menu?")
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setPositiveButton("Back", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent=new Intent(GameEndPage.this,MenuPage.class);
                    startActivity(intent);
                    finish();
                }
            }).show();
            return  true;
        }else{
            return super.onKeyDown(keyCode, event);
        }
    }

}