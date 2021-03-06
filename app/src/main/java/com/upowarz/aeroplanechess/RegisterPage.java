package com.upowarz.aeroplanechess;

import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
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

import java.util.concurrent.atomic.AtomicReference;

import static com.xzavier0722.uon.sabrinaaeroplanechess.common.Utils.base64;

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
        mbBtn_createAccount=findViewById(R.id.btnCreateAccount);

        mbBtn_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getUserName()==null||getUserPassword()==null||getCheckPassword()==null){
                    Toast.makeText(getApplicationContext(),"Cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }else if(getUserName().contains(" ")||getUserPassword().contains(" ")||getCheckPassword().contains(" ")){
                    Toast.makeText(getApplicationContext(),"Cannot contain space", Toast.LENGTH_SHORT).show();
                    return;

                }else if(!getCheckPassword().equals(getUserPassword())){
                    Toast.makeText(getApplicationContext(),"Password is not same", Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    long timestamp = System.currentTimeMillis();
                    String str = Utils.randomString(64);
                    try {

                        AES aes = new AES(Utils.sha256(timestamp+str));
                        String data = Utils.base64(getUserName())+","+Utils.base64(Utils.sha256(getUserPassword()));

                        Packet packet = new Packet();
                        packet.setRequest(Request.REGISTER);
                        packet.setSign(Utils.getSign(data));
                        packet.setData(aes.encrypt(data));
                        packet.setTimestamp(timestamp);
                        packet.setSessionId(str);

                        RequestLock lock = new RequestLock();
                        new Thread(() -> {
                            Sabrina.getRemoteController().requestWithBlocking(lock, RemoteController.loginService, packet);
                        }).start();
                        synchronized (lock) {
                            lock.wait();
                        }
                        String response = lock.getValue();
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                        if (!response.equals("ERROR")) {
                            runOnUiThread(()->{
                                Intent intent = new Intent(RegisterPage.this,LoginPage.class);
                                startActivity(intent);
                            });
                        } else {

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            }
        });
    }


    public String getUserName() { return userName=mET_userName.getText().toString(); }

    public String getUserPassword() { return userPassword=mET_userPassword.getText().toString(); }

    public String getCheckPassword() { return checkPassword=mET_checkUserPassword.getText().toString(); }


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