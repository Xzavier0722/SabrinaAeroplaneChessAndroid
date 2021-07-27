package com.xzavier0722.uon.sabrinaaeroplanechess.android.events.process;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.Player;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.ChessBoard;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event.Event;

import java.util.List;

public class GameEndEvent extends Event {

    private final List<Player> ranking;

    public GameEndEvent(ChessBoard chessBoard, List<Player> ranking) {
        super(chessBoard);
        this.ranking = ranking;
    }

    public List<Player> getRanking() {
        return ranking;
    }
}
