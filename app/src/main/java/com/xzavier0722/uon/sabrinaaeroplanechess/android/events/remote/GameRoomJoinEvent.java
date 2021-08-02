package com.xzavier0722.uon.sabrinaaeroplanechess.android.events.remote;

import com.xzavier0722.uon.sabrinaaeroplanechess.common.game.PlayerProfile;

public class GameRoomJoinEvent extends GameRoomEvent{
    public GameRoomJoinEvent(PlayerProfile profile) {
        super(profile);
    }
}
