package com.xzavier0722.uon.sabrinaaeroplanechess.android;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.chess.Slots;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.event.EventManager;

public class Sabrina {

    private static final EventManager eventManager = new EventManager();
    private static final Slots slots = new Slots();

    public static EventManager getEventManager() {
        return eventManager;
    }

    public static Slots getSlots() {
        return slots;
    }

}
