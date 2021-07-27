package com.xzavier0722.uon.sabrinaaeroplanechess.android.listeners;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.Sabrina;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.Player;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.PlayerType;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Piece;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.SlotType;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event.EventListener;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event.Listener;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event.ListenerType;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.player.PlayerSelectPieceEvent;

import java.util.List;
import java.util.Random;

public class RobotListener implements Listener {

    private final Random random = new Random();

    public RobotListener() {
        Sabrina.getEventManager().registerListener(this);
    }

    @EventListener(type = ListenerType.Listener)
    public void onWaitSelectPieceEvent(PlayerSelectPieceEvent e) {
        Player p = e.getPlayer();
        if (p.isRobot() || p.getType() == PlayerType.ROBOT) {
            List<Piece> availablePieces = e.getAvailablePieces();
            for (Piece each : availablePieces) {
                if (each.getCurrentSlot().getType() == SlotType.HOME_SLOT) {
                    e.setSelectedPiece(each);
                    return;
                }
            }
            e.setSelectedPiece(availablePieces.get(random.nextInt(availablePieces.size())));
        }
    }

}
