package com.xzavier0722.uon.sabrinaaeroplanechess.android.events.process;

import androidx.annotation.NonNull;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.ChessBoard;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event.Event;

public class GameStartEvent extends Event {
    public GameStartEvent(@NonNull ChessBoard chessBoard) {
        super(chessBoard);
    }
}
