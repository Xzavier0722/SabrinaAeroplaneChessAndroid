package com.upowarz.aeroplanechess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.GameLoopTask;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.Sabrina;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.Player;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.PlayerFlag;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.ChessBoard;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.remote.GameRoomLeaveEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.remote.RemoteController;
import com.xzavier0722.uon.sabrinaaeroplanechess.common.Utils;
import com.xzavier0722.uon.sabrinaaeroplanechess.common.game.PlayerProfile;
import com.xzavier0722.uon.sabrinaaeroplanechess.common.networking.Packet;
import com.xzavier0722.uon.sabrinaaeroplanechess.common.networking.Request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public class MutilplayerPage extends AppCompatActivity {

    private ListView mListPlayers;
    private Spinner spColorChoice;
    private Button mbtn_BackMutilplayer;
    private Button btnMutilplayer;
    private TextView tvRoom;
    public List<PlayerProfile> uidPlayer;
    public PlayerAdapter adapter;

    //Set<Player> players;

    public static MutilplayerPage instance;

    private boolean RoomOwner;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutilplayer_page);

        instance = this;

        tvRoom = findViewById(R.id.tv_RoomID);
        mListPlayers = findViewById(R.id.listPlayer);
        mbtn_BackMutilplayer = findViewById(R.id.btnMultiBack);
        btnMutilplayer = findViewById(R.id.btnStartPlay);
        Intent intent = getIntent();
        tvRoom.setText(intent.getStringExtra("RoomNum"));
        RoomOwner = intent.getBooleanExtra("Right",true);
        uidPlayer = new ArrayList<>();
        //players = new HashSet<>();

        Set<PlayerProfile> currentPlayers = CacheManager.get("otherPlayers",Set.class,new HashSet<PlayerProfile>());
        uidPlayer.addAll(currentPlayers);
        uidPlayer.add(CacheManager.get("currentPlayer",PlayerProfile.class,null));


        if(RoomOwner){
            btnMutilplayer.setVisibility(View.VISIBLE);
        }else {
            btnMutilplayer.setVisibility(View.INVISIBLE);
        }


        adapter = new PlayerAdapter(MutilplayerPage.this,uidPlayer,RoomOwner);
        mListPlayers.setAdapter(adapter);



        btnMutilplayer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Set<String> addedColors = new HashSet<>();
                String data = "start";
                for(int i = 0;i < uidPlayer.size();i++){
                    spColorChoice=mListPlayers.getChildAt(i).findViewById(R.id.spColor);
                    String color = spColorChoice.getSelectedItem().toString();

                    if (addedColors.contains(color)) {
                        Toast.makeText(getApplicationContext(),"Color can not be same",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    addedColors.add(color);
                    data+= ","+Utils.base64(uidPlayer.get(i).getName())+":"+color;

                }

                try {
                    RemoteController rc = Sabrina.getRemoteController();
                    Packet packet = new Packet();
                    packet.setRequest(Request.GAME_ROOM);
                    packet.setData(rc.getAes().encrypt(data));
                    packet.setSign(Utils.getSign(data));
                    btnMutilplayer.setEnabled(false);
                    new Thread(() -> {
                        rc.send(RemoteController.gameService, packet, -1);
                    }).start();
                } catch (IllegalBlockSizeException | BadPaddingException e) {
                    e.printStackTrace();
                }

            }
        });


        mbtn_BackMutilplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                    RemoteController rc = Sabrina.getRemoteController();
                    Packet packet = new Packet();
                    packet.setRequest(Request.GAME_ROOM);
                    packet.setData(rc.getAes().encrypt("leave"));
                    packet.setSign(Utils.getSign("leave"));
                    btnMutilplayer.setEnabled(false);
                    new Thread(() -> {
                        rc.send(RemoteController.gameService, packet, -1);
                    }).start();
                    finish();

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

    }


}