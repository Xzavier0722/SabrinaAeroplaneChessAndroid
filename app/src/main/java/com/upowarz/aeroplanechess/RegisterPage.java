package com.upowarz.aeroplanechess;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterPage extends AppCompatActivity {

    private EditText mET_userName;
    private EditText mET_userPassword;
    private EditText mET_checkUserPassword;
    private Button mbBtn_createAccount;

    private String userName;
    private String userPassword;
    private String checkPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        mET_userName=findViewById(R.id.et_RgUserName);
        mET_userPassword=findViewById(R.id.et_RgUserPassword);
        mET_checkUserPassword=findViewById(R.id.et_checkPassword);

        mbBtn_createAccount=findViewById(R.id.btn_SearchRoom);
        mbBtn_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userName==null||userPassword==null||checkPassword==null){
                    Toast.makeText(getApplicationContext(),"Cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }else if(userName.contains(" ")||userPassword.contains(" ")||checkPassword.contains(" ")){
                    Toast.makeText(getApplicationContext(),"Cannot contain space", Toast.LENGTH_SHORT).show();
                    return;
                }else{

                }
            }
        });
    }


    public String getUserName() {
        return userName=mET_userName.getText().toString();
    }

    public String getUserPassword() {
        return userPassword=mET_userPassword.getText().toString();
    }

    public String getCheckPassword() {
        return checkPassword=mET_checkUserPassword.getText().toString();
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure to return?")
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setPositiveButton("Back", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).show();
            return  true;
        }else{
            return super.onKeyDown(keyCode, event);
        }
    }
}