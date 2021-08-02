package com.xzavier0722.uon.sabrinaaeroplanechess.android.events.remote;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event.Event;

public abstract class RemoteEvent extends Event {
    public RemoteEvent() {
        super(null);
        setMultiPlayer(true);
    }
}
