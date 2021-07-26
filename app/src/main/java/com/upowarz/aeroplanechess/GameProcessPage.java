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
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.PlayerFlag;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Location;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Slot;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Slots;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

public class GameProcessPage extends AppCompatActivity {
    private AbsoluteLayout mABLayout;
    private RelativeLayout mRelayout;
    private RelativeLayout.LayoutParams mReParams;
    private LinearLayout.LayoutParams mLParams;
    private LinearLayout linearLayout;
    private AbsoluteLayout.LayoutParams layoutParams;
    private Button mbtnRoll;
    private ChessButton testChess;
    private TextView tvTest;
    private Slots slots;
    private int width;
    private int height;
    private float mapGap;
    private int chessSize;
    private Map<Location, Slot> mapSlots;
    private BlockingQueue<AnimationTask> animationQueue = new LinkedBlockingDeque<>();
    private MediaPlayer player;


    private int lastStep = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        slots = Sabrina.getSlots();
        setContentView(R.layout.activity_game_process_page);
        View mView=getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        mView.setSystemUiVisibility(uiOptions);
        init();
        //Set Ablayout and add into view
        mLParams=new LinearLayout.LayoutParams(height,height);
        mABLayout.setLayoutParams(mLParams);
        mABLayout.setBackground(getResources().getDrawable(R.drawable.game_map));
        linearLayout.addView(mABLayout,0,mLParams);
        layoutParams=new AbsoluteLayout.LayoutParams(chessSize,chessSize,getAxis(3),getAxis(31));
        mABLayout.addView(testChess,layoutParams);

        //tvTest.setText(newMapGap+"|"+height+"|"+mABLayout.getBackground().getIntrinsicHeight()+"|"+mABLayout.getBackground().getMinimumHeight()/36+"|"+mapGap);
        System.out.println("----------"+Sabrina.getSlots()==null+"---------");
        final ExecutorService mThreadPool = Executors.newCachedThreadPool();
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
        mbtnRoll.setOnClickListener(v -> {
            animationMove(testChess,0,10);
            player.start();
        });
    }



    private void init() {
        player=MediaPlayer.create(this,R.raw.go);
        linearLayout=findViewById(R.id.mainLLayout);
        tvTest=findViewById(R.id.tvInfBar);
        mbtnRoll=findViewById(R.id.btnRoll);
        testChess=new ChessButton(this, PlayerFlag.YELLOW);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mABLayout=new AbsoluteLayout(this);
        //mRelayout=(RelativeLayout)findViewById(R.id.ReLayout);
        width = metrics.widthPixels;
        height = metrics.heightPixels;
        mapGap=height/36;
        chessSize=getAxis(2);
        mReParams =new RelativeLayout.LayoutParams(height*2,height);
    }


    private int getAxis(int multiplier) {
        return (int)(mapGap*multiplier);
    }

    /**
     * For set Chess location
     * @param chessButton
     * @param x x location
     * @param y y location
     */
    public void setChessLocation(ChessButton chessButton,int x,int y){
        chessButton.setLayoutParams(new AbsoluteLayout.LayoutParams(chessSize,chessSize,getAxis(x),getAxis(y)));
        chessButton.postInvalidate();
    }

    public void animationMove(ChessButton chessButton,int startPoint,int endSlot)  {
        for (int i=startPoint;i<endSlot;i++){
            int finalI = i;
            animationQueue.offer(new AnimationTask(500) {
                @Override
                public void execute() {
                    runOnUiThread(()->{ // t UI
                        Slot target = slots.getPublicSlots().get(finalI);
                        int moveX = target.getLocation().getX();
                        int moveY = target.getLocation().getY();
                        chessButton.setDirection(target.getFace());
                        chessButton.animate().x(moveX*mapGap).setDuration(200);
                        chessButton.animate().y(moveY*mapGap).setDuration(200);
                    });
                }
            });
        }
        animationQueue.offer(new AnimationTask(500) {
            @Override
            public void execute() {
                testChess.setEnable(true);
            }
        });
        /**
         int moveX = slots.getPublicSlots().get(endSlot).getLocation().getX();
         int moveY = slots.getPublicSlots().get(endSlot).getLocation().getY();
         testChess.animate().x((int)((double)moveX*mapGap)).setDuration(2000);
         testChess.animate().y((int)((double)moveY*mapGap)).setDuration(2000);
         **/

    }

    /**
     * Get current chess x location
     * @param params
     * @return x value
     */
    public int getChessLocationX(AbsoluteLayout.LayoutParams params){
        return  params.x;
    }

    /**
     * Get current chess y location
     * @param params
     * @return y value
     */
    public int getChessLocationY(AbsoluteLayout.LayoutParams params){
        return  params.y;
    }

    private void initLocation(){

    }



}