package com.xzavier0722.uon.sabrinaaeroplanechess.android.events.piece;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.ChessBoard;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Piece;

public class PieceReachedGoalEvent extends PieceEvent{

    private final int restProcessingPiece;

    public PieceReachedGoalEvent(ChessBoard chessBoard, Piece piece) {
        super(chessBoard, piece);
        this.restProcessingPiece = chessBoard.getProcessingPieces(getFlag()).size();
    }

    public int getRestProcessingPiece() {
        return restProcessingPiece;
    }
}
