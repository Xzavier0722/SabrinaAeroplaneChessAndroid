package com.xzavier0722.uon.sabrinaaeroplanechess.android.events.player;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.Player;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.ChessBoard;

public class PlayerTurnStartEvent extends PlayerDiceEvent {

    public PlayerTurnStartEvent(ChessBoard chessBoard, Player player, int diceNum) {
        super(chessBoard, player, diceNum);
    }

}
