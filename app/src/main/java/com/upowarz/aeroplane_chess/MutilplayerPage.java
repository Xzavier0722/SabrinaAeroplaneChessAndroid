package com.upowarz.aeroplane_chess;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class MutilplayerPage extends AppCompatActivity {
    private ListView mListPlayers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutilplayer_page);
        mListPlayers=findViewById(R.id.listPlayer);
        //mListPlayers.setAdapter();
    }
}