package com.xzavier0722.uon.sabrinaaeroplanechess.android.listeners;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.Sabrina;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.PlayerType;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event.EventListener;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event.Listener;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event.ListenerType;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.player.PlayerSelectPieceEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.player.PlayerTurnStartEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.process.GameEndEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.remote.RemotePieceSelectedEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.remote.RemoteTurnStartEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.remote.RemoteController;
import com.xzavier0722.uon.sabrinaaeroplanechess.common.Utils;
import com.xzavier0722.uon.sabrinaaeroplanechess.common.networking.Packet;
import com.xzavier0722.uon.sabrinaaeroplanechess.common.networking.Request;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class RemoteListener implements Listener {

    private final RemoteController rc = Sabrina.getRemoteController();
    BlockingQueue<Integer> responseQueue = new LinkedBlockingDeque<>();

    public RemoteListener() {
        Sabrina.getEventManager().registerListener(this);
    }

    @EventListener
    public void onTurnStart(PlayerTurnStartEvent e) {
        System.out.println("PlayerTurnStartEvent");
        if (!e.isMultiPlayer()) {
            return;
        }

        if (e.getPlayer().getType() == PlayerType.LOCAL) {
            sendPacket("turnStart");
        }

        try {
            e.setDiceNum(responseQueue.take());
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    @EventListener
    public void onPieceSelect(PlayerSelectPieceEvent e) {
        System.out.println("PlayerSelectPieceEvent");
        if (!e.isMultiPlayer()) {
            return;
        }

        if (e.getPlayer().getType() == PlayerType.REMOTE) {
            try {
                e.setSelectedPiece(e.getChessBoard().getPieceById(responseQueue.take()));
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }

    }

    @EventListener(type = ListenerType.Post)
    public void postPieceSelect(PlayerSelectPieceEvent e) {
        System.out.println("PlayerSelectPieceEvent");
        if (!e.isMultiPlayer()) {
            return;
        }

        if (e.getPlayer().getType() == PlayerType.LOCAL) {
            sendPacket("pieceSelected,"+e.getSelectedPiece().getId());
        }
    }

    @EventListener(type = ListenerType.Post)
    public void postGameEnd(GameEndEvent e) {
        sendPacket("gameEnd");
    }

    @EventListener
    public void onRemoteTurnStart(RemoteTurnStartEvent e) {
        System.out.println("RemoteTurnStartEvent");
        responseQueue.offer(e.getDice());
    }

    @EventListener
    public void onRemotePieceSelected(RemotePieceSelectedEvent e) {
        System.out.println("RemotePieceSelectedEvent");
        responseQueue.offer(e.getPieceId());
    }

    private void sendPacket(String data) {
        try {
            Packet p = new Packet();
            p.setRequest(Request.GAME_PROCESS);
            p.setSign(Utils.getSign(data));
            p.setData(rc.getAes().encrypt(data));
            rc.send(RemoteController.gameService, p, -1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
