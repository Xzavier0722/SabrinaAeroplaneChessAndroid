package com.xzavier0722.uon.sabrinaaeroplanechess.android.events;

import androidx.annotation.NonNull;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.PlayerFlag;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.ChessBoard;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Piece;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event.Event;

public abstract class PieceEvent extends Event {

    private final Piece piece;

    public PieceEvent(ChessBoard chessBoard, Piece piece) {
        super(chessBoard);
        this.piece = piece;
    }

    @NonNull
    public Piece getPiece() {
        return piece;
    }

    @NonNull
    public PlayerFlag getFlag() {
        return piece.getFlag();
    }
}
