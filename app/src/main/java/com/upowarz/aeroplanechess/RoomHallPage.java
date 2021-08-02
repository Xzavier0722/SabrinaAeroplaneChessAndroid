package com.upowarz.aeroplanechess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


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
                Intent intent=new Intent(RoomHallPage.this,CreateRoomPage.class);
                startActivity(intent);
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