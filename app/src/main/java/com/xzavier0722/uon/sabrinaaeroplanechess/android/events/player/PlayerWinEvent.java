package com.xzavier0722.uon.sabrinaaeroplanechess.android.events.player;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.Player;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.ChessBoard;

public class PlayerWinEvent extends PlayerEvent{

    private final int restPlayerCount;

    public PlayerWinEvent(ChessBoard chessBoard, Player player) {
        super(chessBoard, player);
        this.restPlayerCount = chessBoard.getPlayingCount();
    }

    public int getRestPlayerCount() {
        return restPlayerCount;
    }
}
