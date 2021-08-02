package com.upowarz.aeroplanechess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.GameLoopTask;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.Player;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.ChessBoard;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class MutilplayerPage extends AppCompatActivity {

    private ListView mListPlayers;
    private Spinner spColorChoice;
    private Button mbtn_BackMutilplayer;
    private Button btnMutilplayer;

    private Boolean RoomOwner;


    private Set<Player> players;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutilplayer_page);

        mListPlayers = findViewById(R.id.listPlayer);
        mbtn_BackMutilplayer = findViewById(R.id.btnMultiBack);
        btnMutilplayer = findViewById(R.id.btnStartPlay);

        RoomOwner=true;

        if(RoomOwner){
            btnMutilplayer.setVisibility(View.VISIBLE);
        }else {
            btnMutilplayer.setVisibility(View.INVISIBLE);
        }


        List<String> testID = Arrays.asList("a", "b", "c");

        PlayerAdapter adapter = new PlayerAdapter(MutilplayerPage.this,testID);
        mListPlayers.setAdapter(adapter);

        for(int i = 0;i < players.size();i++){
            spColorChoice=mListPlayers.getChildAt(i).findViewById(R.id.spColor);
            spColorChoice.getSelectedItem().toString();
        }


        btnMutilplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameLoopTask gameLoopTask=new GameLoopTask(new ChessBoard(players),true);
            }
        });


        mbtn_BackMutilplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /**
        spColorChoice=mListPlayers.getChildAt(0).findViewById(R.id.spColor);
        spColorChoice.getSelectedItem().toString();
         */
    }


}