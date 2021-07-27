package com.xzavier0722.uon.sabrinaaeroplanechess.android.events.piece;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.ChessBoard;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Piece;

public class PieceReachedGoldEvent extends PieceEvent{

    private final int restProcessingPiece;

    public PieceReachedGoldEvent(ChessBoard chessBoard, Piece piece) {
        super(chessBoard, piece);
        this.restProcessingPiece = chessBoard.getProcessingPieces(getFlag()).size();
    }

    public int getRestProcessingPiece() {
        return restProcessingPiece;
    }
}
