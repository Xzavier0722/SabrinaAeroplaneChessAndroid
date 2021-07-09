package com.upowarz.aeroplane_chess;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class RoomHallPage extends AppCompatActivity {

    private ListView roomHallList;
    private List<String>testList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_hall_page);
        testList=new ArrayList<>();
        for(int i=0;i<20;i++){
            testList.add(String.format("%d",i));
        }
        roomHallList=(ListView) findViewById(R.id.list_Room);
        roomHallList.setAdapter(new RoomListAdapter(RoomHallPage.this,testList));
    }
}