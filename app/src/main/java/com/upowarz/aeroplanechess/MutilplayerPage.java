package com.upowarz.aeroplanechess;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class MutilplayerPage extends AppCompatActivity {
    private ListView mListPlayers;
    private Spinner spColorChoice;
    private Set<Player> players;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutilplayer_page);
        mListPlayers = findViewById(R.id.listPlayer);
        List<String> testID = Arrays.asList("a", "b", "c");
        PlayerAdapter adapter = new PlayerAdapter(MutilplayerPage.this,testID);
        mListPlayers.setAdapter(adapter);
        /**
        spColorChoice=mListPlayers.getChildAt(0).findViewById(R.id.spColor);
        spColorChoice.getSelectedItem().toString();
         */
    }


}