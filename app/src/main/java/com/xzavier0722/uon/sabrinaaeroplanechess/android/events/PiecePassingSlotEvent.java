package com.xzavier0722.uon.sabrinaaeroplanechess.android.events;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.ChessBoard;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Piece;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Slot;

public class PiecePassingSlotEvent extends PieceOnSlotEvent {

    private boolean abort;

    public PiecePassingSlotEvent(ChessBoard chessBoard, Piece piece, Slot slot) {
        super(chessBoard, piece, slot);
        abort = false;
    }

    public void setAbort(boolean abort) {
        this.abort = abort;
    }

    public boolean isAbort() {
        return abort;
    }
}
