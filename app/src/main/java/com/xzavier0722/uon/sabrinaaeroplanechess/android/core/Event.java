package com.xzavier0722.uon.sabrinaaeroplanechess.android.core;

import androidx.annotation.NonNull;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.objects.ChessBoard;

public abstract class Event {

    private String name;
    private final ChessBoard chessBoard;

    public Event(@NonNull ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

    @NonNull
    public String getEventName() {
        if (this.name == null) {
            this.name = this.getClass().getSimpleName();
        }
        return this.name;
    }

    @NonNull
    public ChessBoard getChessBoard() {
        return chessBoard;
    }

}
