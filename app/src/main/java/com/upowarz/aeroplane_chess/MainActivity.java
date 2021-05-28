package com.upowarz.aeroplane_chess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button mbtnStartPage;
    private Button mbtnLoginPage;
    private Button mbtnMenuPage;
    private Button mbtnGameProcessPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }
    private void initView(){
        Onclick onclick=new Onclick();
        mbtnStartPage=(Button) findViewById(R.id.btnTStart);
        mbtnLoginPage=(Button) findViewById(R.id.btnTLogin);
        mbtnMenuPage=(Button) findViewById(R.id.btnTMenu);
        mbtnGameProcessPage=(Button) findViewById(R.id.btnTGameProcessPage);
        mbtnStartPage.setOnClickListener(onclick);
        mbtnLoginPage.setOnClickListener(onclick);
        mbtnMenuPage.setOnClickListener(onclick);
        mbtnGameProcessPage.setOnClickListener(onclick);
    }

    private class Onclick implements View.OnClickListener{
        @Override
        public void onClick(View view){
            Intent i=null;
            switch (view.getId()){
                case R.id.btnTStart:
                    i=new Intent(MainActivity.this,StartPage.class);
                    break;
                case R.id.btnTLogin:
                    i=new Intent(MainActivity.this,LoginPage.class);
                    break;
                case R.id.btnTGameProcessPage:
                    i=new Intent(MainActivity.this,GameProcessPage.class);
                    break;
                case R.id.btnTMenu:
                    i=new Intent(MainActivity.this,MenuPage.class);
                    break;
            }
            startActivity(i);
        }
    }
}