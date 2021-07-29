package com.upowarz.aeroplanechess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.GameLoopTask;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.Player;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.PlayerFlag;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.PlayerType;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.ChessBoard;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class SinglePlayerPage extends AppCompatActivity {
    private List<RadioGroup> radioGroupList;
    private List<EditText> editTextList;
    private Button mbtnSingle;
    private Set<Player> playerSet;

    public static SinglePlayerPage instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player_page);
        init();

    }

    private void init(){
        playerSet = new HashSet<>();
        editTextList=new ArrayList<>();
        radioGroupList=new ArrayList<>();
        for(int i=0;i<4;i++){
            int edText = 0;
            int raidoGroupID=0;
            try {
                edText = R.id.class.getField("edTxV"+(i+1)).getInt(null);
                raidoGroupID=R.id.class.getField("rdBtnG"+(i+1)).getInt(null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
           editTextList.add(findViewById(edText));
            radioGroupList.add(findViewById(raidoGroupID));
        }

        radioGroupList.get(0).check(R.id.rdBtnB2);
        radioGroupList.get(1).check(R.id.rdBtnGG2);
        radioGroupList.get(2).check(R.id.rdBtnR2);
        radioGroupList.get(3).check(R.id.rdBtnY2);

        mbtnSingle=findViewById(R.id.btnStartSingle);
        final ExecutorService mThreadPool = Executors.newCachedThreadPool();
        mbtnSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mbtnSingle.setEnabled(false);
                check();
                GameLoopTask task = new GameLoopTask(new ChessBoard(playerSet));
                mThreadPool.execute(task);
            }
        });

        for(int i=0;i<4;i++){
            int finalI = i;
            radioGroupList.get(i).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    PlayerFlag[] values = PlayerFlag.values();
                    RadioButton rd=group.findViewById(checkedId);
                    String str = rd.getText().toString();

                    EditText et = editTextList.get(finalI);

                    et.setEnabled(str.equals("Player"));
                    et.setText(str.equals("Robot") ? "Robot "+values[finalI].toString() : str);
//
//                    if(rd.getText().equals("Robot")){
//                        editTextList.get(finalI).setText();
//                        editTextList.get(finalI).setEnabled(false);
//                    }else if(rd.getText().equals("Player")){
//                        editTextList.get(finalI).setEnabled(true);
//                    }else if(rd.getText().equals("None")) {
//                        editTextList.get(finalI).setText("None");
//                        editTextList.get(finalI).setEnabled(false);
//                    }
                }
            });
        }


    }

    private void check() {
        PlayerFlag[] values = PlayerFlag.values();
        for (int i = 0; i < 4; i++) {
            RadioGroup radioGroup = radioGroupList.get(i);
            getPlayer(values[i], ((RadioButton)radioGroup.findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString()).ifPresent(v -> playerSet.add(v));
        }
    }

    private Optional<Player> getPlayer(PlayerFlag flag, String typeStr) {
        if (typeStr.equals("None")) return Optional.empty();
        return Optional.of(new Player(flag, editTextList.get(flag.ordinal()).getText().toString(), typeStr.equals("Robot") ? PlayerType.ROBOT : PlayerType.LOCAL));
    }

}