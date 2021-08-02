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

public class RemoteListener implements Listener {

    private final RemoteController rc = Sabrina.getRemoteController();
    private volatile int lastDice = 0;
    private volatile int lastPieceId = 0;

    public RemoteListener() {
        Sabrina.getEventManager().registerListener(this);
    }

    @EventListener
    public void onTurnStart(PlayerTurnStartEvent e) {
        if (!e.isMultiPlayer()) {
            return;
        }

        if (e.getPlayer().getType() == PlayerType.LOCAL) {
            sendPacket("turnStart");
        }

        try {
            wait();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        e.setDiceNum(lastDice);
    }

    @EventListener
    public void onPieceSelect(PlayerSelectPieceEvent e) {
        if (!e.isMultiPlayer()) {
            return;
        }

        if (e.getPlayer().getType() == PlayerType.REMOTE) {
            try {
                wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        e.setSelectedPiece(e.getChessBoard().getPieceById(lastPieceId));
    }

    @EventListener(type = ListenerType.Post)
    public void postPieceSelect(PlayerSelectPieceEvent e) {
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
        this.lastDice = e.getDice();
        notifyAll();
    }

    @EventListener
    public void onRemoteTurnStart(RemotePieceSelectedEvent e) {
        this.lastPieceId = e.getPieceId();
        notifyAll();
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
