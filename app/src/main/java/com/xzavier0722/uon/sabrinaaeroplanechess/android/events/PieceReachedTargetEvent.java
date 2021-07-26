package com.xzavier0722.uon.sabrinaaeroplanechess.android.events;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.ChessBoard;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Piece;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Slot;

public class PieceReachedTargetEvent extends PieceOnSlotEvent {

    public PieceReachedTargetEvent(ChessBoard chessBoard, Piece piece, Slot slot) {
        super(chessBoard, piece, slot);
    }
}
