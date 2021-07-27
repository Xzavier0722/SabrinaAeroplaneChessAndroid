package com.xzavier0722.uon.sabrinaaeroplanechess.android;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event.EventManager;

public class Sabrina {

    private static final EventManager eventManager = new EventManager();

    public static EventManager getEventManager() {
        return eventManager;
    }

}
