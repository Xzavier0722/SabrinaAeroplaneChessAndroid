package com.xzavier0722.uon.sabrinaaeroplanechess.android;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.Player;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.ChessBoard;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Piece;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Slot;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event.Event;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event.EventListener;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event.Listener;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event.ListenerType;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.piece.PieceDropBackEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.piece.PieceMoveEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.piece.PiecePassingSlotEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.piece.PieceReachedTargetEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.player.PlayerSelectPieceEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.player.PlayerTurnEndEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.player.PlayerTurnStartEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.player.PlayerWinEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.process.GameEndEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.process.GameStartEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GameLoopTask implements Runnable, Listener {

    private final ChessBoard chessBoard;
    private final Random random = new Random();
    private final List<Player> ranking = new ArrayList<>();

    public GameLoopTask(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
        Sabrina.getEventManager().registerListener(this);
    }

    @Override
    public void run() {

        callEvent(new GameStartEvent(chessBoard));
        do {
            doRound();
        } while (chessBoard.getPlayingCount() > 1);
        callEvent(new GameEndEvent(chessBoard, ranking));
    }

    private void doRound() {
        chessBoard.forEachFlag(flag -> {
            if (chessBoard.isWon(flag)) return;

            Player p = chessBoard.getPlayer(flag);
            int dice;
            int count = 0;
            do {
                count++;

                if (count > 2) {
                    for (Piece each : chessBoard.getProcessingPieces(flag)) {
                        callEvent(new PieceDropBackEvent(chessBoard, each));
                    }
                    return;
                }

                dice = 1 + random.nextInt(6);
                // Call turn start event
                callEvent(new PlayerTurnStartEvent(chessBoard, p, dice));

                // Call select event
                List<Piece> availablePieces = getAvailablePieces(chessBoard.getProcessingPieces(flag),dice);
                if (availablePieces.size() <= 0) {
                    callEvent(new PlayerTurnEndEvent(chessBoard, p));
                    continue;
                }
                PlayerSelectPieceEvent selectPieceEvent = callEvent(new PlayerSelectPieceEvent(chessBoard, p, dice, availablePieces));

                // Get selected piece
                Piece selected = selectPieceEvent.getSelectedPiece();
                if (selected == null) {
                    throw new IllegalStateException("No selected piece specified");
                }

                Slot lastSlot = selected.getCurrentSlot();
                // Call piece move event
                PieceMoveEvent moveEvent = callEvent(new PieceMoveEvent(chessBoard, selected, dice));

                // Move piece
                for (int i = 0; i < moveEvent.getTotalStep(); i++) {
                    Slot nextSlot = chessBoard.getSlots().getNext(flag, lastSlot);
                    if (nextSlot == null) {
                        break;
                    }
                    PiecePassingSlotEvent passingSlotEvent = callEvent(new PiecePassingSlotEvent(chessBoard, selected, nextSlot));
                    lastSlot = nextSlot;
                    if (passingSlotEvent.isAbort()) {
                        break;
                    }
                }

                // Call reached target event
                callEvent(new PieceReachedTargetEvent(chessBoard, selected, lastSlot));

                // Call turn end event
                callEvent(new PlayerTurnEndEvent(chessBoard, p));
            }while (dice == 6);
        });
    }

    private <T extends Event> T callEvent(T e) {
        Sabrina.getEventManager().callListener(e);
        return e;
    }

    private List<Piece> getAvailablePieces(Set<Piece> processingPiece, int dice) {
        List<Piece> re = new ArrayList<>();
        for (Piece each : processingPiece) {
            switch (each.getCurrentSlot().getType()) {
                case HOME_SLOT:
                    if (each.isWon() || dice < 5) {
                        continue;
                    }
                case START_SLOT:
                case PUBLIC_SLOT:
                case PRIVATE_SLOT:
                    re.add(each);
                    break;
            }
        }
        return re;
    }

    @EventListener(type = ListenerType.Monitor)
    public void onPlayerWinEvent(PlayerWinEvent e) {
        ranking.add(e.getPlayer());
    }
}
