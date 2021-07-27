package com.xzavier0722.uon.sabrinaaeroplanechess.android.events.player;

import androidx.annotation.NonNull;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.Flagged;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.Player;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.PlayerFlag;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.ChessBoard;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event.Event;

public abstract class PlayerEvent extends Event implements Flagged {

    private final Player player;

    public PlayerEvent(ChessBoard chessBoard, Player player) {
        super(chessBoard);
        this.player = player;
    }

    @Override
    public PlayerFlag getFlag() {
        return player.getFlag();
    }

    public Player getPlayer() {
        return player;
    }
}
