package com.xzavier0722.uon.sabrinaaeroplanechess.android.events;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.ChessBoard;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Piece;

public class PieceReachedGoldEvent extends PieceEvent{
    public PieceReachedGoldEvent(ChessBoard chessBoard, Piece piece) {
        super(chessBoard, piece);
    }
}
