package com.xzavier0722.uon.sabrinaaeroplanechess.android.events.remote;

public class GameRoomKickEvent extends RemoteEvent{

    private final String uuid;

    public GameRoomKickEvent(String uuid) {

        this.uuid = uuid;

    }

    public String getUuid() {
        return uuid;
    }
}
