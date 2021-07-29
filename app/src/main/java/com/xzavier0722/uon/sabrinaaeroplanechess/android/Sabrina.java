package com.xzavier0722.uon.sabrinaaeroplanechess.android;

import com.upowarz.aeroplanechess.ListenerChessBoard;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event.EventManager;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.listeners.ChessBoardMonitor;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.listeners.PieceListener;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.listeners.RobotListener;

public class Sabrina {

    private static final EventManager eventManager = new EventManager();

    static {
        new PieceListener();
        new RobotListener();
        new ChessBoardMonitor();
        new ListenerChessBoard();
    }

    public static EventManager getEventManager() {
        return eventManager;
    }

}
