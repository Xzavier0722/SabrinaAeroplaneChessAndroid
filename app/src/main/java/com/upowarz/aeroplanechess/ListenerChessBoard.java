package com.upowarz.aeroplanechess;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.Sabrina;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.Flagged;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.Player;
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
    }

    @EventListener
    public void onPlayerTurn(PlayerTurnStartEvent e){
        GameProcessPage.instance.mbtnRoll.setEnabled(true);
        CacheManager.put("eventPlayerTurn", e);
        GameProcessPage.instance.setMtvInfoBar(e.getPlayer().getName()+"'s turn! Roll!");
        while(!GameProcessPage.instance.result){
            try {
                Thread.sleep(300);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
        GameProcessPage.instance.setMtvInfoBar(e.getPlayer().getName()+"Roll"+e.getDiceNum());
        GameProcessPage.instance.result=false;
    }

    @EventListener
    public void onSelectPieceEvent(PlayerSelectPieceEvent e) {
        Set<ChessButton> enabled = new HashSet<>();
        Thread t = Thread.currentThread();
        for (Piece piece: e.getAvailablePieces()) {
            ChessButton btn = getBtn(e, piece);
            btn.setEnable(true);
            enabled.add(btn);
            btn.setOnClickListener(v -> {
                e.setSelectedPiece(btn.getPiece());
                for (ChessButton cb : enabled) {
                    cb.setEnable(false);
                }
                t.notify();
            });
            try {
                t.wait();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }

    @EventListener
    public void onGameEndEvent(GameEndEvent e){
        CacheManager.put("rankList", e.getRanking());
        Intent intent=new Intent(GameProcessPage.instance,GameEndEvent.class);
        GameProcessPage.instance.startActivity(intent);
    }

    @EventListener(type = ListenerType.Monitor)
    public void onPiecePassingSlotEvent(PiecePassingSlotEvent e){
        GameProcessPage.instance.animationMove(getBtn(e, e.getPiece()), e.getSlot(), 200, 300);
    }

    @EventListener(type = ListenerType.Monitor)
    public void onPiecePassingTrackEvent(PiecePassingTrackEvent e) {
        GameProcessPage.instance.animationMove(getBtn(e, e.getPiece()),e.getTargetSlot(), 200, 2000);
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

}
