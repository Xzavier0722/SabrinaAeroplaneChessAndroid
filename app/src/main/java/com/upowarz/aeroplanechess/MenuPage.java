package com.upowarz.aeroplanechess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuPage extends AppCompatActivity {

    private Button btnCancel;
    private Button btnJumpMutilply;
    private Button btnJumpSingle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);

        btnCancel = findViewById(R.id.btnBackMenu);
        btnJumpMutilply = findViewById(R.id.btnMultiply);
        btnJumpSingle = findViewById(R.id.btnSingle);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnJumpMutilply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPage.this,LoginPage.class);
                startActivity(intent);
            }
        });

        btnJumpSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPage.this,SinglePlayerPage.class);
                startActivity(intent);
            }
        });



    }
}