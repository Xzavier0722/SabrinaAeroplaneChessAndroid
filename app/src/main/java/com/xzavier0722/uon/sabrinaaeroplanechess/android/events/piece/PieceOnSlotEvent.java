package com.xzavier0722.uon.sabrinaaeroplanechess.android.events.piece;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.ChessBoard;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Piece;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Slot;

public abstract class PieceOnSlotEvent extends PieceEvent{

    private final Slot slot;

    public PieceOnSlotEvent(ChessBoard chessBoard, Piece piece, Slot slot) {
        super(chessBoard, piece);
        this.slot = slot;
    }

    public Slot getSlot() {
        return slot;
    }
}
