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
import com.xzavier0722.uon.sabrinaaeroplanechess.common.networking.Packet;
import com.xzavier0722.uon.sabrinaaeroplanechess.common.networking.Request;
import com.xzavier0722.uon.sabrinaaeroplanechess.common.security.AES;


public class RoomHallPage extends AppCompatActivity {

    private EditText edRoomNum;
    private Button btnSearchRoom;
    private Button mbtn_CreateRoom;
    private Button mbtn_backRoomHall;


    private String roomNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_hall_page);

        btnSearchRoom=findViewById(R.id.btn_SearchRoom);
        edRoomNum = findViewById(R.id.et_RoomNum);
        mbtn_CreateRoom = findViewById(R.id.btn_CreateRoom);
        mbtn_backRoomHall = findViewById(R.id.btnBackMenu);


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

                    String message = lock.getValue();
                    if(message=="ERROR"){
                        Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT);
                        return;
                    }

                    runOnUiThread(()->{
                        Intent intent=new Intent(RoomHallPage.this,MutilplayerPage.class);
                        System.out.println(message);
                        intent.putExtra("RoomNum",message);
                        startActivity(intent);
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        mbtn_backRoomHall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private String getRoomNum(){return roomNum=edRoomNum.getText().toString();}
}