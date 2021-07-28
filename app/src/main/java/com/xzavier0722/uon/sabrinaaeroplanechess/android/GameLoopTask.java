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

import java.lang.reflect.Constructor;
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

        callEvent(GameStartEvent.class);
        do {
            doRound();
        } while (chessBoard.getPlayingCount() > 1);
        callEvent(GameEndEvent.class, ranking);
    }

    private void doRound() {
        chessBoard.forEachFlag(flag -> {
            if (!chessBoard.isWon(flag)) {
                Player p = chessBoard.getPlayer(flag);
                int dice;
                int count = 0;
                do {
                    count++;

                    if (count > 3) {
                        for (Piece each : chessBoard.getProcessingPieces(flag)) {
                            callEvent(PieceDropBackEvent.class, each);
                        }
                    }

                    dice = random.nextInt(7);
                    // Call turn start event
                    callEvent(PlayerTurnStartEvent.class, p, dice);

                    // Call select event
                    PlayerSelectPieceEvent selectPieceEvent = callEvent(PlayerSelectPieceEvent.class, p, dice, getAvailablePieces(chessBoard.getProcessingPieces(flag),dice));

                    // Get selected piece
                    Piece selected = selectPieceEvent.getSelectedPiece();
                    if (selected == null) {
                        throw new IllegalStateException("No selected piece specified");
                    }

                    Slot nextSlot = chessBoard.getSlots().getNext(flag, selected.getCurrentSlot());
                    // Call piece move event
                    PieceMoveEvent moveEvent = callEvent(PieceMoveEvent.class, selected, dice);

                    // Move piece
                    for (int i = 0; i < moveEvent.getTotalStep(); i++) {
                        if (nextSlot == null) {
                            break;
                        }
                        PiecePassingSlotEvent passingSlotEvent = callEvent(PiecePassingSlotEvent.class, selected, nextSlot);
                        if (passingSlotEvent.isAbort()) {
                            break;
                        }
                        nextSlot = chessBoard.getSlots().getNext(flag, selected.getCurrentSlot());
                    }

                    // Call reached target event
                    callEvent(PieceReachedTargetEvent.class, selected, nextSlot);

                    // Call turn end event
                    callEvent(PlayerTurnEndEvent.class, p);
                }while (dice == 6);
            }
        });
    }

    private <T extends Event> T callEvent(Class<T> tClass, Object... paras) {
        try {
            Constructor<?>[] constructors = tClass.getConstructors();
            T event = (T) constructors[0].newInstance(chessBoard, paras);
            Sabrina.getEventManager().callListener(event);
            return event;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("Exception thrown while calling the event: "+tClass.getSimpleName());
        }
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
