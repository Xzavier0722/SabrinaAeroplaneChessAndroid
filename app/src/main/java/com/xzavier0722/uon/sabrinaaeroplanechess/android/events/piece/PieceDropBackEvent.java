package com.xzavier0722.uon.sabrinaaeroplanechess.android.events.piece;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.ChessBoard;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Piece;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Slot;

public class PieceDropBackEvent extends PieceFromToEvent{
    public PieceDropBackEvent(ChessBoard chessBoard, Piece piece, Slot slot) {
        super(chessBoard, piece, slot, piece.getHomeSlot());
    }
}
