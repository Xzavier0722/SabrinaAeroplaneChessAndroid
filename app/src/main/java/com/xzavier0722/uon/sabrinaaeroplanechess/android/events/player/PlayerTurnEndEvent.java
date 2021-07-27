package com.xzavier0722.uon.sabrinaaeroplanechess.android.events.player;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.Player;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.ChessBoard;

public class PlayerTurnEndEvent extends PlayerEvent {
    public PlayerTurnEndEvent(ChessBoard chessBoard, Player player) {
        super(chessBoard, player);
    }
}
