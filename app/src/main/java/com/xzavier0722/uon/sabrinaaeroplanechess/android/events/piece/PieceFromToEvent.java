package com.xzavier0722.uon.sabrinaaeroplanechess.android.events.piece;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.ChessBoard;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Piece;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Slot;

public abstract class PieceFromToEvent extends PieceOnSlotEvent{

    private final Slot targetSlot;

    public PieceFromToEvent(ChessBoard chessBoard, Piece piece, Slot slot, Slot targetSlot) {
        super(chessBoard, piece, slot);
        this.targetSlot = targetSlot;
    }

    public Slot getTargetSlot() {
        return targetSlot;
    }
}
