package com.xzavier0722.uon.sabrinaaeroplanechess.android.events.remote;

import com.xzavier0722.uon.sabrinaaeroplanechess.common.game.PlayerProfile;

public class GameRoomLeaveEvent extends GameRoomEvent{
    public GameRoomLeaveEvent(PlayerProfile profile) {
        super(profile);
    }
}
