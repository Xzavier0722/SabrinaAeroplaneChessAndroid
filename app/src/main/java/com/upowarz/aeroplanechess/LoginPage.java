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
import com.xzavier0722.uon.sabrinaaeroplanechess.common.game.PlayerProfile;

public class LoginPage extends AppCompatActivity {

    private String userAccount;
    private String userPassword;

    private Button btnLogin;
    private Button btnBack;
    private Button btnRegister;
    private EditText etAccount;
    private EditText etPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        btnLogin = (Button)findViewById(R.id.btn_Login);
        btnBack = findViewById(R.id.btnLoginBack);
        etAccount = (EditText)findViewById(R.id.et_Account);
        etPassword = (EditText)findViewById(R.id.et_Password);
        btnRegister=findViewById(R.id.btn_Register);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check user account and password from database
                PlayerProfile check;
                try {
                    RemoteController remoteController= Sabrina.getRemoteController();
                    check=remoteController.login(getUserAccount(),getUserPassword());
                    if (check != null){
                        Intent intent=new Intent(LoginPage.this,MutilplayerPage.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(),"Login failed",Toast.LENGTH_SHORT);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginPage.this,RegisterPage.class);
                startActivity(intent);
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