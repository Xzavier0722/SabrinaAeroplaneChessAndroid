package com.upowarz.aeroplanechess;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;

import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.Player;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.PlayerFlag;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.ChessBoard;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Location;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Piece;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Slot;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.process.GameStartEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.common.threading.QueuedExecutionThread;
import com.xzavier0722.uon.sabrinaaeroplanechess.common.threading.QueuedTask;

import java.util.HashMap;
import java.util.Map;

public class GameProcessPage extends AppCompatActivity{

    public AbsoluteLayout mABLayout;
    private LinearLayout linearLayout;
    private GridLayout mGrPlayerList;


    public Button mbtnRoll;
    public TextView mtvInfoBar;
    public Map<Player,TextView>setMtvPlayerList;


    private int height;
    private float mapGap;
    public int chessSize;
    public boolean result;

    private MediaPlayer soundPlayer;
    private Map<PlayerFlag, Map<Integer, ChessButton>> chessButtons;
    private ChessBoard chessBoard;

    private DiceImage diceImage;
    private QueuedExecutionThread animationThread;


    public static GameProcessPage instance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_process_page);

        //hide status Bar
        View mView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        mView.setSystemUiVisibility(uiOptions);

        //init
        init();

        //Set Ablayout and add into view
        LinearLayout.LayoutParams mLParams = new LinearLayout.LayoutParams(height, height);
        mABLayout.setBackground(getResources().getDrawable(R.drawable.game_map));
        linearLayout.addView(mABLayout,0, mLParams);

        //Call listener
        GameStartEvent gameStartEvent=CacheManager.get("eventInit",GameStartEvent.class,null);

        this.chessBoard = gameStartEvent.getChessBoard();
        initChessBoard(gameStartEvent.getChessBoard());

        //Call Thread
        animationThread = new QueuedExecutionThread();
        animationThread.start();

        synchronized (gameStartEvent) {
            gameStartEvent.notifyAll();
        }
    }


    private void init() {
        mABLayout = new AbsoluteLayout(this);
        linearLayout = findViewById(R.id.mainLLayout);
        mbtnRoll = findViewById(R.id.btnRoll);
        mtvInfoBar = findViewById(R.id.tvInfBar);
        mGrPlayerList = findViewById(R.id.GrPlayerList);

        chessButtons = new HashMap<>();
        setMtvPlayerList = new HashMap<>();
        soundPlayer = MediaPlayer.create(this,R.raw.go);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        height = metrics.heightPixels;
        mapGap = height/36;
        chessSize = getAxis(2);

        //Create Click event
        mbtnRoll.setOnClickListener(v -> {
            soundPlayer.start();
            result=true;
        });
    }


    public int getAxis(int multiplier) {
        return (int)(mapGap*multiplier);
    }


    public void animationMove(ChessButton chessButton,Slot target, int waitTime, int duration)  {
        scheduleAnimation(new AnimationTask(duration+waitTime) {
            @Override
            public void execute() {
                int moveX = target.getLocation().getX();
                int moveY = target.getLocation().getY();
                chessButton.setDirection(target.getFace());
                chessButton.animate().x(moveX*mapGap).setDuration(duration);
                chessButton.animate().y(moveY*mapGap).setDuration(duration);
            }
        });
    }

    public void animationDiceMove(PlayerFlag flag, int diceNum)  {
        scheduleAnimation(new AnimationTask(50) {
            @Override
            public void execute() {
                DiceImage last = diceImage;
                diceImage = new DiceImage(GameProcessPage.instance, diceNum);
                Location l = chessBoard.getSlots().getPrepareSlot(flag).getLocation();
                AbsoluteLayout.LayoutParams layoutParams = new AbsoluteLayout.LayoutParams(chessSize,chessSize,getAxis(l.getX()),getAxis(l.getY()));
                mABLayout.addView(diceImage,layoutParams);

                if (last != null) {
                    mABLayout.removeView(last);
                }
            }
        });
        scheduleAnimation(new AnimationTask(1200) {
            @Override
            public void execute() {
                diceImage.animate().x(getAxis(17)).setDuration(1000);
                diceImage.animate().y(getAxis(17)).setDuration(1000);
            }
        });
    }


    public void setMtvInfoBar(String text){
        mtvInfoBar.setText(text);
    }




    public void scheduleAnimation(AnimationTask task) {
        animationThread.schedule(new QueuedTask() {
            @Override
            public void execute() {
                runOnUiThread(() -> task.execute());
            }

            @Override
            public int getDelay() {
                return task.getWaitTime();
            }
        });
    }


    private void initChessBoard(ChessBoard chessBoard){

        StringBuilder sb = new StringBuilder();

        for(Player player:chessBoard.getPlayers()){
            Map<Integer, ChessButton> flaggedBtns = new HashMap<>();
            sb.append(player.getName()+"\n");

            for(Piece piece:chessBoard.getPieces(player.getFlag())){
                ChessButton chessButton = new ChessButton(this,piece);
                chessButton.setEnable(false);
                flaggedBtns.put(piece.getId(), chessButton);
                Location l = piece.getCurrentSlot().getLocation();
                AbsoluteLayout.LayoutParams layoutParams = new AbsoluteLayout.LayoutParams(chessSize,chessSize,getAxis(l.getX()),getAxis(l.getY()));
                mABLayout.addView(chessButton,layoutParams);
            }

            chessButtons.put(player.getFlag(), flaggedBtns);

            ImageView imageView=new ImageView(this);
            TextView textView=new TextView(this);
            textView.setText(player.getName());
            textView.setGravity(Gravity.CENTER);
            textView.setCompoundDrawablePadding(10);
            int id=1;
            imageView.setId(id);

            GridLayout.Spec rowSpec = null;
            GridLayout.Spec columnSpec = null;
            GridLayout.LayoutParams layoutParams;

            RelativeLayout relativeLayout = new RelativeLayout(this);
            relativeLayout.setGravity(Gravity.CENTER);
            RelativeLayout.LayoutParams reParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);


            if(player.getFlag()==PlayerFlag.BLUE){
                rowSpec=GridLayout.spec(1, 1);
                columnSpec=GridLayout.spec(0, 1);
                imageView.setBackground(getDrawable(R.drawable.blue));
            }else if(player.getFlag()==PlayerFlag.GREEN) {
                rowSpec=GridLayout.spec(0, 1);
                columnSpec=GridLayout.spec(0, 1);
                imageView.setBackground(getDrawable(R.drawable.green));
            }else if(player.getFlag()==PlayerFlag.RED){
                rowSpec=GridLayout.spec(0, 1);
                columnSpec=GridLayout.spec(1, 1);
                imageView.setBackground(getDrawable(R.drawable.red));
            }else if(player.getFlag()==PlayerFlag.YELLOW){
                rowSpec=GridLayout.spec(1, 1);
                columnSpec=GridLayout.spec(1, 1);
                imageView.setBackground(getDrawable(R.drawable.yellow));
            }


            imageView.setLayoutParams(new RelativeLayout.LayoutParams(chessSize,chessSize));
            relativeLayout.addView(imageView);

            reParams.addRule(RelativeLayout.RIGHT_OF,imageView.getId());
            reParams.setMargins(10,10,10,10);
            textView.setLayoutParams(reParams);
            relativeLayout.addView(textView);

            layoutParams = new GridLayout.LayoutParams(rowSpec,columnSpec);
            layoutParams.setMargins(100,50,50,50);
            layoutParams.setGravity(Gravity.LEFT);

            mGrPlayerList.addView(relativeLayout,layoutParams);
            setMtvPlayerList.put(player,textView);

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK){
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure to return the menu?")
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setPositiveButton("Back", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent=new Intent(GameProcessPage.this,MenuPage.class);
                    startActivity(intent);
                    finish();
                }
            }).show();
            return  true;
        }else{
            return super.onKeyDown(keyCode, event);
        }
    }


    public Map<Integer, ChessButton> getChessButtons(PlayerFlag flag){
        return chessButtons.get(flag);
    }

}