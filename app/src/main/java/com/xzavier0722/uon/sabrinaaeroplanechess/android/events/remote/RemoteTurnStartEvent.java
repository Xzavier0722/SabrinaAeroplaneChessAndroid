package com.xzavier0722.uon.sabrinaaeroplanechess.android.events.remote;

public class RemoteTurnStartEvent extends RemoteEvent{

    private final int dice;

    public RemoteTurnStartEvent(int dice) {
        this.dice = dice;
    }

    public int getDice() {
        return dice;
    }

}
