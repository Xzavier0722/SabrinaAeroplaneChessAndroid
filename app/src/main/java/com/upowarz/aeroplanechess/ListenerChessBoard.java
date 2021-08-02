package com.upowarz.aeroplanechess;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.Image;
import android.nfc.Tag;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.Sabrina;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.Flagged;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.Player;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.PlayerFlag;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.PlayerType;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.ChessBoard;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Location;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Piece;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Slot;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event.EventListener;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event.Listener;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event.ListenerType;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.piece.PieceDropBackEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.piece.PieceMoveEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.piece.PiecePassingSlotEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.piece.PiecePassingTrackEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.piece.PieceReachedGoalEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.piece.PieceReachedTargetEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.piece.PieceSkipEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.player.PlayerSelectPieceEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.player.PlayerTurnEndEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.player.PlayerTurnStartEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.player.PlayerWinEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.process.GameEndEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.process.GameStartEvent;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.content.ContentValues.TAG;

public class ListenerChessBoard implements Listener {

    private Map<Slot,TextView> mpCheckOverlap;

    public ListenerChessBoard(){
        Sabrina.getEventManager().registerListener(this);
        mpCheckOverlap = new HashMap<>();
    }

    @EventListener
    public void init(GameStartEvent e){
        CacheManager.put("eventInit", e);


        SinglePlayerPage.instance.runOnUiThread( () -> {
            Intent jump=new Intent(SinglePlayerPage.instance,GameProcessPage.class);
            SinglePlayerPage.instance.startActivity(jump);
        });


        try {
            synchronized (e) {
                e.wait();
            }
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }


    }

    @EventListener
    public void onPlayerTurn(PlayerTurnStartEvent e){

        String str=e.getPlayer().getName()+" turn! Roll!";
        setText(GameProcessPage.instance.mtvInfoBar,str);

        if (e.getPlayer().getType() != PlayerType.LOCAL) return;

        GameProcessPage.instance.scheduleAnimation(new AnimationTask(20) {
            @Override
            public void execute() {
                GameProcessPage.instance.mbtnRoll.setEnabled(true);
            }
        });

        CacheManager.put("eventPlayerTurn", e);


        GameProcessPage.instance.mbtnRoll.setOnClickListener(v->{
            synchronized (e) {
                e.notifyAll();
                GameProcessPage.instance.mbtnRoll.setEnabled(false);
            }
        });


        synchronized (e) {
            try {
                e.wait();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }

    @EventListener(type = ListenerType.Post)
    public void onPostPlayerTurn(PlayerTurnStartEvent e) {
        String str=e.getPlayer().getName()+" Rolled "+e.getDiceNum();
        setText(GameProcessPage.instance.mtvInfoBar,str);
        GameProcessPage.instance.animationDiceMove(e.getFlag(), e.getDiceNum());
    }




    @EventListener
    public void onSelectPieceEvent(PlayerSelectPieceEvent e) {
        if (e.getPlayer().getType() != PlayerType.LOCAL) return;

        Set<ChessButton> enabled = new HashSet<>();
        Thread t = Thread.currentThread();

        GameProcessPage.instance.scheduleAnimation(new AnimationTask(20) {
            @Override
            public void execute() {
                for (Piece piece: e.getAvailablePieces()) {
                    ChessButton btn = getBtn(e, piece);
                    btn.setEnable(true);
                    enabled.add(btn);
                    btn.setOnClickListener(v -> {
                        e.setSelectedPiece(btn.getPiece());
                        for (ChessButton cb : enabled) {
                            cb.setEnable(false);
                        }
                        synchronized (t) {
                            t.notify();
                        }
                    });
                }
            }
        });


        try {
            synchronized (t) {
                t.wait();
            }
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }


    @EventListener
    public void onGameEndEvent(GameEndEvent e){

        CacheManager.put("rankList", e.getRanking());

        GameProcessPage.instance.scheduleAnimation(new AnimationTask(0) {
            @Override
            public void execute() {
                Intent intent=new Intent(GameProcessPage.instance,GameEndPage.class);
                GameProcessPage.instance.startActivity(intent);
            }
        });

    }

    @EventListener(type = ListenerType.Monitor)
    public void onPiecePassingSlotEvent(PiecePassingSlotEvent e){
        GameProcessPage.instance.animationMove(getBtn(e, e.getPiece()), e.getSlot(), 200, 300);
    }

    @EventListener(type = ListenerType.Monitor)
    public void onPlayerTurnEnd(PlayerTurnEndEvent e) {

        GameProcessPage.instance.scheduleAnimation(new AnimationTask(0) {

            @Override
            public void execute() {
                synchronized (e) {
                    e.notifyAll();
                }
            }
        });

        synchronized (e) {
            try {
                e.wait();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }

    }




    @EventListener(type = ListenerType.Monitor)
    public void onPiecePassingTrackEvent(PiecePassingTrackEvent e) {
        GameProcessPage.instance.animationMove(getBtn(e,e.getPiece()),e.getTargetSlot(),200,1000);
    }

    @EventListener(type = ListenerType.Monitor)
    public void onPieceSkipEvent(PieceSkipEvent e){
        GameProcessPage.instance.animationMove(getBtn(e,e.getPiece()),e.getTargetSlot(),200,1000);
    }

    @EventListener(type = ListenerType.Monitor)
    public void onPieceDropBackEvent(PieceDropBackEvent e){
        GameProcessPage.instance.animationMove(getBtn(e,e.getPiece()),e.getTargetSlot(),200,1000);
    }



    @EventListener(type = ListenerType.Post)
    public void onCheckPieceMoveEvent(PieceMoveEvent e) {
        if (!e.isRequireUpdate()) return;
        checkOverlaped(e.getCurrentSlot(), null);
    }

    @EventListener(type = ListenerType.Post)
    public void onCheckPieceReachedTargetEvent(PieceReachedTargetEvent e) {
        if (!e.isRequireUpdate()) return;
        checkOverlaped(null, e.getSlot());
    }

    @EventListener(type = ListenerType.Post)
    public void onCheckPiecePassingTrackEvent(PiecePassingTrackEvent e) {
        if (!e.isRequireUpdate()) return;
        checkOverlaped(null, e.getTargetSlot());
    }

    @EventListener(type = ListenerType.Post)
    public void onCheckPieceSkipEvent(PieceSkipEvent e){
        if (!e.isRequireUpdate()) return;
        checkOverlaped(null, e.getTargetSlot());
    }

    @EventListener(type = ListenerType.Post)
    public void onCheckPieceDropBackEvent(PieceDropBackEvent e){
        if (!e.isRequireUpdate()) return;
        checkOverlaped(e.getSlot(), e.getTargetSlot());
    }



    @EventListener(type = ListenerType.Monitor)
    public void onPlayerWinEvent(PlayerWinEvent e){
        GameProcessPage.instance.scheduleAnimation(new AnimationTask(0) {
            @Override
            public void execute() {
                if(GameProcessPage.instance.setMtvPlayerList.containsKey(e.getPlayer())){
                    GameProcessPage.instance.setMtvPlayerList.get(e.getPlayer()).setTextColor(Color.RED);
                }else{
                    return;
                }
            }
        });
    }

    @EventListener(type = ListenerType.Monitor)
    public void onPieceReachedGoalEvent(PieceReachedGoalEvent e){
        GameProcessPage.instance.animationMove(getBtn(e,e.getPiece()),e.getPiece().getHomeSlot(),200,1000);
        GameProcessPage.instance.scheduleAnimation(new AnimationTask(0) {
            @Override
            public void execute() {
                Typeface font=Typeface.createFromAsset(GameProcessPage.instance.getAssets(),"iconfont.ttf");
                TextView textView = new TextView(GameProcessPage.instance);
                textView.setTypeface(font);
                textView.setText(R.string.finish);
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(Color.GREEN);
                Location l = e.getPiece().getHomeSlot().getLocation();
                AbsoluteLayout.LayoutParams layoutParams = new AbsoluteLayout.LayoutParams(GameProcessPage.instance.chessSize,GameProcessPage.instance.chessSize,GameProcessPage.instance.getAxis(l.getX()),GameProcessPage.instance.getAxis(l.getY()));
                GameProcessPage.instance.mABLayout.addView(textView,layoutParams);
            }
        });

    }

    private ChessButton getBtn(Flagged f, Piece p) {
        return GameProcessPage.instance.getChessButtons(f.getFlag()).get(p.getId());
    }

    private void setText(TextView tv, String text) {
        GameProcessPage.instance.scheduleAnimation(new AnimationTask(0) {
            @Override
            public void execute() {
                tv.setText(text);
            }
        });
    }

     private void checkOverlaped(Slot source, Slot target){

         if(source != null){
             final int count = source.getPieces().size()-1;
             TextView tv = mpCheckOverlap.get(source);
             if (tv != null) {
                 if (count > 1) {
                     GameProcessPage.instance.scheduleAnimation(new AnimationTask(20) {
                         @Override
                         public void execute() {
                             tv.setText("x"+(count));
                         }
                     });
                 } else {
                     GameProcessPage.instance.scheduleAnimation(new AnimationTask(20) {
                         @Override
                         public void execute() {
                             GameProcessPage.instance.mABLayout.removeView(mpCheckOverlap.remove(source));
                         }
                     });
                 }
             }

         }

         if(target != null){
             final int count = target.getPieces().size();
             if (count < 2) return;



             if(mpCheckOverlap.containsKey(target)){
                 TextView textView = mpCheckOverlap.get(target);
                 GameProcessPage.instance.scheduleAnimation(new AnimationTask(20) {
                     @Override
                     public void execute() {
                         textView.setText("x"+count);
                     }
                 });
             }else{
                 TextView textView = new TextView(GameProcessPage.instance);
                 textView.setText("x"+count);
                 textView.setGravity(Gravity.CENTER);
                 if(target.getOccupiedFlag()== PlayerFlag.RED){
                     textView.setTextColor(Color.WHITE);
                 }else{
                     textView.setTextColor(Color.rgb(238,105,17));
                 }
                 textView.getPaint().setFakeBoldText(true);
                 Location l = target.getLocation();
                 AbsoluteLayout.LayoutParams layoutParams = new AbsoluteLayout.LayoutParams(GameProcessPage.instance.chessSize,GameProcessPage.instance.chessSize,GameProcessPage.instance.getAxis(l.getX()),GameProcessPage.instance.getAxis(l.getY()));
                 GameProcessPage.instance.scheduleAnimation(new AnimationTask(20) {
                     @Override
                     public void execute() {
                         GameProcessPage.instance.mABLayout.addView(textView,layoutParams);
                     }
                 });
                 mpCheckOverlap.put(target,textView);
             }
         }
     }

}
