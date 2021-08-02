package com.xzavier0722.uon.sabrinaaeroplanechess.android.events.player;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.Player;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.ChessBoard;

public abstract class PlayerDiceEvent extends PlayerEvent{

    private int diceNum;

    public PlayerDiceEvent(ChessBoard chessBoard, Player player, int diceNum) {
        super(chessBoard, player);
        this.diceNum = diceNum;
    }

    public void setDiceNum(int diceNum) {
        this.diceNum = diceNum;
    }

    public int getDiceNum() {
        return diceNum;
    }
}
