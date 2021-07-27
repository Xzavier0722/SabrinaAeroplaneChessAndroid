package com.xzavier0722.uon.sabrinaaeroplanechess.android.events.piece;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.ChessBoard;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Piece;

public class PieceDropBackEvent extends PieceFromToEvent{
    public PieceDropBackEvent(ChessBoard chessBoard, Piece piece) {
        super(chessBoard, piece, piece.getCurrentSlot(), piece.getHomeSlot());
    }
}
