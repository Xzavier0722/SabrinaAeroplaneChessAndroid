package com.xzavier0722.uon.sabrinaaeroplanechess.android;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event.EventManager;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.listeners.PieceListener;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.listeners.RobotListener;

public class Sabrina {

    private static final EventManager eventManager = new EventManager();

    static {
        new PieceListener();
        new RobotListener();
    }

    public static EventManager getEventManager() {
        return eventManager;
    }

}
