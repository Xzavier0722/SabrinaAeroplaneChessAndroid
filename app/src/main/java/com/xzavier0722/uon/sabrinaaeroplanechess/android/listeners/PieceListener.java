package com.xzavier0722.uon.sabrinaaeroplanechess.android.listeners;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.Sabrina;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.Flagged;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.ChessBoard;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Piece;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Slot;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.SlotType;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event.EventListener;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event.Listener;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.piece.PieceDropBackEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.piece.PieceMoveEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.piece.PiecePassingSlotEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.piece.PiecePassingTrackEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.piece.PieceReachedGoalEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.piece.PieceReachedTargetEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.piece.PieceSkipEvent;

import java.util.ArrayList;
import java.util.List;

public class PieceListener implements Listener {

    public PieceListener() {
        Sabrina.getEventManager().registerListener(this);
    }

    @EventListener
    public void onPassingSlot(PiecePassingSlotEvent e) {
        Slot slot = e.getSlot();
        if (isAgainst(slot, e)) {
            List<Piece> pieces = slot.getPieces();
            if (pieces.size() > 1) {
                e.setAbort(true);
                // Drop back all pieces in this slot
                dropBackAll(e.getChessBoard(), pieces);
            }
        }
    }

    @EventListener
    public void onMove(PieceMoveEvent e) {
        Slot slot = e.getPiece().getCurrentSlot();
        if (slot.getType() == SlotType.HOME_SLOT) {
            int rawStep = e.getRawStep();
            e.setBonusStep(-rawStep+1);
        }
    }

    @EventListener
    public void onReachTarget(PieceReachedTargetEvent e) {
        Slot s = e.getSlot();
        Piece p = e.getPiece();
        if (s.getType() == SlotType.TARGET_SLOT) {
            // Arrived the goal slot
            e.addSubEvent(new PieceReachedGoalEvent(e.getChessBoard(), p));
            e.setRequireUpdate(false);
            return;
        }
        if (!s.isLast() && s.getType() == SlotType.PUBLIC_SLOT && s.getFlag() == p.getFlag()) {
            e.addSubEvent(
                    s.isOnTrack() ?
                    new PiecePassingTrackEvent(e.getChessBoard(), p, s) :
                    new PieceSkipEvent(e.getChessBoard(), p, s)
            );
            e.setRequireUpdate(false);
            return;
        }
        if (isAgainst(s, e)) {
            dropBackAll(e.getChessBoard(), s.getPieces());
        }
    }

    @EventListener
    public void onSkipEvent(PieceSkipEvent e) {
        Slot s = e.getTargetSlot();
        Piece p = e.getPiece();
        if (s.isOnTrack()) {
            e.addSubEvent(new PiecePassingTrackEvent(e.getChessBoard(), p, s));
            e.setRequireUpdate(false);
            return;
        }
        if (isAgainst(s, p)) {
            dropBackAll(e.getChessBoard(), s.getPieces());
        }
    }

    @EventListener
    public void onPassingTrack(PiecePassingTrackEvent e) {
        Slot s = e.getTargetSlot();
        if (isAgainst(s, e)) {
            dropBackAll(e.getChessBoard(), s.getPieces());
        }
    }

    private void dropBackAll(ChessBoard chessBoard, List<Piece> pieces) {
        pieces = new ArrayList<>(pieces);
        for (Piece each : pieces) {
            Sabrina.getEventManager().callListener(new PieceDropBackEvent(chessBoard, each));
        }
    }

    private boolean isAgainst(Slot s, Flagged f) {
        return s.isOccupied() && s.getOccupiedFlag() != f.getFlag();
    }

}
