package com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event;

import androidx.annotation.NonNull;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.ChessBoard;

public abstract class Event {

    private final ChessBoard chessBoard;

    public Event(@NonNull ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

    @NonNull
    public ChessBoard getChessBoard() {
        return chessBoard;
    }

}
