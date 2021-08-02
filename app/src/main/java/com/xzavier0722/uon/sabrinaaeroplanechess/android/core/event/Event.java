package com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.ChessBoard;

import java.util.LinkedList;
import java.util.List;

public abstract class Event {

    private final ChessBoard chessBoard;
    // Record if a ChessBoard update required
    private boolean requireUpdate;
    private final List<Event> subEvents;

    private boolean multiPlayer;

    public Event(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
        requireUpdate = true;
        multiPlayer = false;
        subEvents = new LinkedList<>();
    }

    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    public void setRequireUpdate(boolean requireUpdate) {
        this.requireUpdate = requireUpdate;
    }

    public boolean isRequireUpdate() {
        return requireUpdate;
    }

    public void addSubEvent(Event e) {
        subEvents.add(e);
    }

    public List<Event> getSubEvents() {
        return subEvents;
    }

    public boolean isMultiPlayer() {
        return multiPlayer;
    }

    public void setMultiPlayer(boolean multiPlayer) {
        this.multiPlayer = multiPlayer;
    }
}
