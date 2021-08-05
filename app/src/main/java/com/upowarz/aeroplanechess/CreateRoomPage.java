package com.upowarz.aeroplanechess;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class CreateRoomPage extends AppCompatActivity{

    private RadioGroup mrdGMember;
    private RadioGroup mrdGRange;
    private Button btnbackCreateRoom;
    private Button btnCreateRoom;

    private int numPlayers;
    private String range;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room_page);

        btnbackCreateRoom = findViewById(R.id.btnCreateBack);
        btnbackCreateRoom.setOnClickListener(v -> finish());

        mrdGMember = findViewById(R.id.rdSelectNum);
        mrdGMember.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton rd = group.findViewById(checkedId);
            String str = rd.getText().toString();
            numPlayers = Integer.valueOf(str);
        });

        mrdGRange=findViewById(R.id.rBtnGRange);
        mrdGRange.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton rd = group.findViewById(checkedId);
            range = rd.getText().toString();
        });


        btnCreateRoom = findViewById(R.id.btn_CreateRoom);
        btnCreateRoom.setOnClickListener(v->{



        });



    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public String getRange() {
        return range;
    }

}

