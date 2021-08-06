package com.upowarz.aeroplanechess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.Sabrina;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.remote.RemoteController;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.remote.RequestLock;
import com.xzavier0722.uon.sabrinaaeroplanechess.common.Utils;
import com.xzavier0722.uon.sabrinaaeroplanechess.common.game.PlayerProfile;
import com.xzavier0722.uon.sabrinaaeroplanechess.common.networking.Packet;
import com.xzavier0722.uon.sabrinaaeroplanechess.common.networking.Request;
import com.xzavier0722.uon.sabrinaaeroplanechess.common.security.AES;

import java.util.HashSet;
import java.util.Set;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;


public class RoomHallPage extends AppCompatActivity {

    private EditText edRoomNum;
    private Button btnSearchRoom;
    private Button mbtn_CreateRoom;
    private Button mbtn_backRoomHall;
    private Button mbtn_Search;
    public static RoomHallPage instance;
    private String message;


    private String roomNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_hall_page);

        instance=this;

        btnSearchRoom=findViewById(R.id.btn_SearchRoom);
        edRoomNum = findViewById(R.id.et_RoomNum);
        mbtn_CreateRoom = findViewById(R.id.btn_CreateRoom);
        mbtn_backRoomHall = findViewById(R.id.btnBackMenu);
        mbtn_Search=findViewById(R.id.btn_SearchRoom);


        btnSearchRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getRoomNum().equals("")){
                   Toast.makeText(getApplicationContext(),"Cannot be empty", Toast.LENGTH_SHORT).show();
                }else{

                    //Jump RoomPage

                }
            }
        });

        mbtn_CreateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AES aes = Sabrina.getRemoteController().getAes();
                try {

                    Packet packet = new Packet();
                    packet.setRequest(Request.GAME_ROOM);
                    String data = "create";
                    packet.setSign(Utils.getSign(data));
                    packet.setData(aes.encrypt(data));

                    RequestLock lock = new RequestLock();

                    new Thread(()->{
                        Sabrina.getRemoteController().requestWithBlocking(lock,RemoteController.gameService,packet);
                    }).start();
                    synchronized (lock){
                        lock.wait();
                    }

                    message = lock.getValue();
                    if(message=="ERROR"){
                        Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT);
                        return;
                    }

                    runOnUiThread(()->{
                        Intent intent=new Intent(RoomHallPage.this,MutilplayerPage.class);
                        intent.putExtra("RoomNum",message);
                        intent.putExtra("Right",true);
                        startActivity(intent);
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        mbtn_Search.setOnClickListener(v->{
            if(edRoomNum.getText()==null){
                return;
            }
            try {
                Set<PlayerProfile> players = join(edRoomNum.getText().toString());
                if (!players.isEmpty()) {
                    // Success
                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                    runOnUiThread(()->{
                        Intent intent = new Intent(RoomHallPage.this,MutilplayerPage.class);
                        intent.putExtra("Right",false);
                        intent.putExtra("RoomNum",edRoomNum.getText().toString());
                        CacheManager.put("otherPlayers",players);
                        startActivity(intent);



                    });
                } else {
                    // ERROR
                    Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });




        mbtn_backRoomHall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private Set<PlayerProfile> join(String roomCode) throws BadPaddingException, IllegalBlockSizeException, InterruptedException {
        Packet p = new Packet();

        String data = "join,"+roomCode;
        p.setData(Sabrina.getRemoteController().getAes().encrypt(data));
        p.setSign(Utils.getSign(data));
        p.setRequest(Request.GAME_ROOM);

        RequestLock lock = new RequestLock();
        new Thread(()->{
            Sabrina.getRemoteController().requestWithBlocking(lock, RemoteController.gameService, p);
        }).start();
        synchronized (lock) {
            lock.wait();
        }
        String[] response = lock.getValue().split(",");
        Set<PlayerProfile> re = new HashSet<>();
        if (response[0].equals("0")) {
            for (int i = 1; i < response.length; i++) {
                re.add(Utils.getGson().fromJson(new String(Utils.debase64(response[i])), PlayerProfile.class));
            }
        }
        return re;
    }

    private String getRoomNum(){return roomNum=edRoomNum.getText().toString();}
}