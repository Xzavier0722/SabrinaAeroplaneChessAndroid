package com.upowarz.aeroplanechess;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.Sabrina;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event.EventListener;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event.Listener;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.remote.GameRoomJoinEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.common.game.PlayerProfile;

import java.util.UUID;

public class GameRoomListener implements Listener {
     public GameRoomListener() {
          Sabrina.getEventManager().registerListener(this);
     }

     @EventListener
     public void onGameRoomJoin(GameRoomJoinEvent e){
          MutilplayerPage.instance.uidPlayer.put(getUuid(e.getProfile()),getName(e.getProfile()));

     }



     private String getName(PlayerProfile playerProfile){
          return playerProfile.getName();
     }

     private UUID getUuid(PlayerProfile playerProfile){
          return playerProfile.getUuid();
     }
}

