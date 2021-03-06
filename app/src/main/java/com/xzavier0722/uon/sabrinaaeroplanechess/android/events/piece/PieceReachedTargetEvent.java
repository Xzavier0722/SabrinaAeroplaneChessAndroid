package com.xzavier0722.uon.sabrinaaeroplanechess.android.events.piece;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.ChessBoard;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Piece;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Slot;

public class PieceReachedTargetEvent extends PieceOnSlotEvent {

    private final boolean aborted;

    public PieceReachedTargetEvent(ChessBoard chessBoard, Piece piece, Slot slot, boolean aborted) {
        super(chessBoard, piece, slot);
        this.aborted = aborted;
    }

    public boolean isAborted() {
        return aborted;
    }
}
