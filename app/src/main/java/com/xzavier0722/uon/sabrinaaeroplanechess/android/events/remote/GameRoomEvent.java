package com.xzavier0722.uon.sabrinaaeroplanechess.android.events.remote;

import com.xzavier0722.uon.sabrinaaeroplanechess.common.game.PlayerProfile;

public abstract class GameRoomEvent extends RemoteEvent{

    private final PlayerProfile profile;

    public GameRoomEvent(PlayerProfile profile) {
        this.profile = profile;
    }


    public PlayerProfile getProfile() {
        return profile;
    }
}
