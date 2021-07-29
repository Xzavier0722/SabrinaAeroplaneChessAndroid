package com.upowarz.aeroplanechess;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.Sabrina;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.Player;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.PlayerFlag;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.ChessBoard;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Location;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Piece;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Slot;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.SlotType;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Slots;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event.EventListener;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event.Listener;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.player.PlayerTurnStartEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.process.GameStartEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

public class GameProcessPage extends AppCompatActivity{

    private AbsoluteLayout mABLayout;
    private LinearLayout.LayoutParams mLParams;
    private LinearLayout linearLayout;


    public Button mbtnRoll;
    public TextView mtvInfoBar;
    public TextView mtvPlayerList;


    private int height;
    private float mapGap;
    private int chessSize;
    public boolean result;


    private Slots slots;
    private BlockingQueue<AnimationTask> animationQueue = new LinkedBlockingDeque<>();
    private MediaPlayer soundPlayer;
    private List<Player>playerList;
    private Map<PlayerFlag, Map<Integer, ChessButton>> chessButtons;


    public static GameProcessPage instance;

    private int lastStep = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance = this;
        super.onCreate(savedInstanceState);
        slots = new Slots();
        setContentView(R.layout.activity_game_process_page);

        //hide status Bar
        View mView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        mView.setSystemUiVisibility(uiOptions);

        //init
        init();

        //Set Ablayout and add into view
        mLParams=new LinearLayout.LayoutParams(height,height);
        mABLayout.setBackground(getResources().getDrawable(R.drawable.game_map));
        linearLayout.addView(mABLayout,0,mLParams);

        //Call listener
        GameStartEvent gameStartEvent=CacheManager.get("eventInit",GameStartEvent.class,null);

        initChessBoard(gameStartEvent.getChessBoard());

        //Call Thread
        ExecutorService mThreadPool = Executors.newCachedThreadPool();
        mThreadPool.execute(()->{
            while (true) {
                try{
                    AnimationTask task = animationQueue.take();
                    task.execute();
                    Thread.sleep(task.getWaitTime());
                }catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
        synchronized (gameStartEvent) {
            gameStartEvent.notifyAll();
        }
    }


    private void init() {

        mABLayout = new AbsoluteLayout(this);
        linearLayout = findViewById(R.id.mainLLayout);
        mbtnRoll = findViewById(R.id.btnRoll);
        mtvInfoBar = findViewById(R.id.tvInfBar);
        mtvPlayerList = findViewById(R.id.tvPlayerList);

        chessButtons = new HashMap<>();
        playerList = new ArrayList<>();
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


    private int getAxis(int multiplier) {
        return (int)(mapGap*multiplier);
    }


    public void animationMove(ChessButton chessButton,Slot target, int waitTime, int duration)  {
        animationQueue.offer(new AnimationTask(duration+waitTime) {
            @Override
            public void execute() {
                runOnUiThread(()->{ // t UI
                    int moveX = target.getLocation().getX();
                    int moveY = target.getLocation().getY();
                    chessButton.setDirection(target.getFace());
                    chessButton.animate().x(moveX*mapGap).setDuration(duration);
                    chessButton.animate().y(moveY*mapGap).setDuration(duration);
                });
            }
        });
    }


    public void setMtvInfoBar(String text){
        mtvInfoBar.setText(text);
    }


    public void setMtvPlayerList(String text){
        mtvPlayerList.setText(text);
    }

    public void scheduleAnimation(AnimationTask task) {
        animationQueue.offer(task);
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
        }
        setMtvPlayerList(sb.toString());
    }





    public Map<Integer, ChessButton> getChessButtons(PlayerFlag flag){
        return chessButtons.get(flag);
    }





}