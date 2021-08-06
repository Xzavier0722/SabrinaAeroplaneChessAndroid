package com.upowarz.aeroplanechess;

import android.content.Intent;

import androidx.annotation.UiThread;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.GameLoopTask;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.Sabrina;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.Player;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.PlayerFlag;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.PlayerType;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.ChessBoard;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event.EventListener;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event.Listener;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.remote.GameRoomJoinEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.remote.GameRoomKickEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.remote.GameRoomLeaveEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.remote.RemoteGameStartEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.common.Utils;
import com.xzavier0722.uon.sabrinaaeroplanechess.common.game.PlayerProfile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ListenerGameRoom implements Listener {
     public ListenerGameRoom() {
          Sabrina.getEventManager().registerListener(this);
     }

     @EventListener
     public void onGameRoomStart(RemoteGameStartEvent e){

          Set<Player> playerList=new HashSet<>();
          PlayerProfile profile = CacheManager.get("currentPlayer", PlayerProfile.class, null);
          e.getPlayerList().forEach(str -> {
               String[] each = str.split(":");
               String name = new String(Utils.debase64(each[0]));
               PlayerFlag flag = PlayerFlag.valueOf(each[1].toUpperCase());
               playerList.add(new Player(flag,name, name.equals(profile.getName()) ? PlayerType.LOCAL : PlayerType.REMOTE));
          });

          GameLoopTask gameLoopTask = new GameLoopTask(new ChessBoard(playerList),true);
          new Thread(gameLoopTask).start();
     }

     @EventListener
     public void onGameRoomJoin(GameRoomJoinEvent e){
          MutilplayerPage.instance.runOnUiThread(()->{
               //MutilplayerPage.instance.uidPlayer.add(e.getProfile());
               MutilplayerPage.instance.adapter.addPlayer(e.getProfile());
          });

     }


     @EventListener
     public void onGameRoomKickEvent (GameRoomKickEvent e){

          MutilplayerPage.instance.runOnUiThread(()->{

               PlayerProfile playerProfile = CacheManager.get("currentPlayer",PlayerProfile.class,null);
               MutilplayerPage.instance.adapter.deletePlayer(e.getUuid());
               if(playerProfile.getUuid().toString().equals(e.getUuid())){
                    MutilplayerPage.instance.finish();
               }


          });

     }

     @EventListener
     public void onGameRoomLeave (GameRoomLeaveEvent e){

          MutilplayerPage.instance.runOnUiThread(()->{
               MutilplayerPage.instance.adapter.deletePlayer(e.getProfile().getUuid().toString());
          });

     }



     private String getName(PlayerProfile playerProfile){
          return playerProfile.getName();
     }

     private UUID getUuid(PlayerProfile playerProfile){
          return playerProfile.getUuid();
     }
}

