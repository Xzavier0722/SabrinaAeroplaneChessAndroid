package com.upowarz.aeroplanechess;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginPage extends AppCompatActivity {
    private String userAccount;
    private String userPassword;
    private Button btnLogin;
    private EditText etAccount;
    private EditText etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        btnLogin=(Button)findViewById(R.id.btn_Login);
        etAccount=(EditText)findViewById(R.id.et_Account);
        etPassword=(EditText)findViewById(R.id.et_Password);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check user account and password from database

            }
        });

    }
    //Get Account
    public String getUserAccount(){
        userAccount=etAccount.getText().toString();
        return userAccount;
    }
    //Get Password
    public String getUserPassword(){
        userPassword=etPassword.getText().toString();
        return userPassword;
    }
}