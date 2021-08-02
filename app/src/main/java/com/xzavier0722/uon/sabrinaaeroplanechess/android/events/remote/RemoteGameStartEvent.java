package com.xzavier0722.uon.sabrinaaeroplanechess.android.events.remote;

import java.util.List;

public class RemoteGameStartEvent extends RemoteEvent{

    private final List<String> playerList;

    public RemoteGameStartEvent(List<String> playerList) {
        this.playerList = playerList;
    }


    public List<String> getPlayerList() {
        return playerList;
    }
}
