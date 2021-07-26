package com.upowarz.aeroplanechess;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

public class MutilplayerPage extends AppCompatActivity {
    private ListView mListPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutilplayer_page);
        mListPlayers = findViewById(R.id.listPlayer);
        List<String> testID = Arrays.asList("a", "b", "c");
        PlayerAdapter adapter = new PlayerAdapter(MutilplayerPage.this,testID);
        mListPlayers.setAdapter(adapter);
    }


}