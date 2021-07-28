package com.xzavier0722.uon.sabrinaaeroplanechess.android.listeners;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.Sabrina;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.ChessBoard;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Piece;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Slot;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event.EventListener;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event.Listener;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event.ListenerType;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.piece.PieceDropBackEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.piece.PiecePassingTrackEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.piece.PieceReachedGoalEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.piece.PieceReachedTargetEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.piece.PieceSkipEvent;

public class ChessBoardMonitor implements Listener {

    public ChessBoardMonitor() {
        Sabrina.getEventManager().registerListener(this);
    }

    @EventListener(type = ListenerType.Monitor)
    public void onReachTarget(PieceReachedTargetEvent e) {
        if (!e.isRequireUpdate()) return;
        moveIfNoAgainst(e.getChessBoard(), e.getPiece(), e.getSlot());
    }

    @EventListener(type = ListenerType.Monitor)
    public void onSkip(PieceSkipEvent e) {
        if (!e.isRequireUpdate()) return;
        moveIfNoAgainst(e.getChessBoard(), e.getPiece(), e.getTargetSlot());
    }

    @EventListener(type = ListenerType.Monitor)
    public void onPassingTrack(PiecePassingTrackEvent e) {
        if (!e.isRequireUpdate()) return;
        e.getChessBoard().setPieceToSlot(e.getPiece(), e.getTargetSlot());
    }

    @EventListener(type = ListenerType.Monitor)
    public void onDropBack(PieceDropBackEvent e) {
        if (!e.isRequireUpdate()) return;
        Piece p = e.getPiece();
        e.getChessBoard().setPieceToSlot(p, p.getHomeSlot());
    }

    @EventListener(type = ListenerType.Monitor)
    public void onReachGoal(PieceReachedGoalEvent e) {
        if (!e.isRequireUpdate()) return;
        Piece p = e.getPiece();
        p.setWon();
        e.getChessBoard().setPieceToSlot(p, p.getHomeSlot());
    }

    private void moveIfNoAgainst(ChessBoard chessBoard, Piece p, Slot s) {
        if (!s.isOccupied() || s.getOccupiedFlag() == p.getFlag()) {
            chessBoard.setPieceToSlot(p, s);
        }
    }

}
