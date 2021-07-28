package com.upowarz.aeroplanechess;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class CreateRoomPage extends AppCompatActivity{
    private Spinner spMember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room_page);
        spMember=(Spinner)findViewById(R.id.spRoomMember);
        String[]numMember=new String[]{"1","2","3","4"};
        ArrayAdapter<String> roomAdapter=new ArrayAdapter<String>(this,R.layout.spinner_text_item,numMember);
        roomAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spMember.setAdapter(roomAdapter);

    }
}