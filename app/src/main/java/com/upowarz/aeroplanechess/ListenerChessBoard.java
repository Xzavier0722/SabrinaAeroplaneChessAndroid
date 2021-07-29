package com.upowarz.aeroplanechess;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.Sabrina;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.Flagged;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.Player;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.PlayerType;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.ChessBoard;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Piece;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event.EventListener;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event.Listener;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event.ListenerType;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.piece.PieceDropBackEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.piece.PieceMoveEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.piece.PiecePassingSlotEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.piece.PiecePassingTrackEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.piece.PieceSkipEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.player.PlayerSelectPieceEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.player.PlayerTurnEndEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.player.PlayerTurnStartEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.process.GameEndEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.process.GameStartEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListenerChessBoard implements Listener {
    public ListenerChessBoard(){
        Sabrina.getEventManager().registerListener(this);
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
        GameProcessPage.instance.runOnUiThread(() -> GameProcessPage.instance.mbtnRoll.setEnabled(true));
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

    @EventListener(type = ListenerType.Monitor)
    public void onPostPlayerTurn(PlayerTurnStartEvent e) {
        String str=e.getPlayer().getName()+" Rolled "+e.getDiceNum();
        setText(GameProcessPage.instance.mtvInfoBar,str);
    }

    @EventListener
    public void onSelectPieceEvent(PlayerSelectPieceEvent e) {
        if (e.getPlayer().getType() != PlayerType.LOCAL) return;
        Set<ChessButton> enabled = new HashSet<>();
        Thread t = Thread.currentThread();
        GameProcessPage.instance.runOnUiThread(()->{
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
        GameProcessPage.instance.runOnUiThread(()->{
            Intent intent=new Intent(GameProcessPage.instance,GameEndPage.class);
            GameProcessPage.instance.startActivity(intent);
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
        GameProcessPage.instance.animationMove(getBtn(e,e.getPiece()),e.getTargetSlot(),200,2000);
    }

    @EventListener(type = ListenerType.Monitor)
    public void onPieceSkipEvent(PieceSkipEvent e){
        GameProcessPage.instance.animationMove(getBtn(e,e.getPiece()),e.getTargetSlot(),200,2000);
    }

    @EventListener(type = ListenerType.Monitor)
    public void onPieceDropBackEvent(PieceDropBackEvent e){
        GameProcessPage.instance.animationMove(getBtn(e,e.getPiece()),e.getTargetSlot(),200,2000);
    }


    private ChessButton getBtn(Flagged f, Piece p) {
        return GameProcessPage.instance.getChessButtons(f.getFlag()).get(p.getId());
    }

    private void setText(TextView tv, String text) {
        GameProcessPage.instance.runOnUiThread(() -> {
            tv.setText(text);
        });
    }

}
