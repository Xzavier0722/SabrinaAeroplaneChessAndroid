package com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.Player;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.PlayerFlag;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class ChessBoard {

    private final Map<PlayerFlag, Player> players = new HashMap<>();
    private final Map<PlayerFlag, Set<Piece>> pieces = new HashMap<>();
    private final Slots slots = new Slots();

    public ChessBoard(Set<Player> players) {

        if (players.size() > 4) {
            throw new IllegalArgumentException("Player count must be less than 4");
        }

        // Add players
        for (Player each : players) {
            PlayerFlag flag = each.getFlag();
            if (this.players.containsKey(flag)) {
                throw new IllegalArgumentException("The flag already exists in this ChessBoard: "+flag.name());
            }

            this.players.put(flag, each);
        }

        // Add pieces
        forEachFlag(flag -> {
            Set<Piece> newPieces = new HashSet<>();
            int baseId = flag.ordinal()*4;
            for (int i = 0; i < 4; i++) {
                Piece piece = new Piece(flag, baseId++);
                Slot home = slots.getHomeSlots(flag).get(i);
                piece.setHomeSlot(home);
                setPieceToSlot(piece, home);
                newPieces.add(piece);
            }
            pieces.put(flag, newPieces);
        });

    }

    /**
     * Execute the {@link Consumer} for every flag by order of Blue, Green, Red, Yellow.
     * @param task: the task will be invoke.
     */
    public void forEachFlag(Consumer<PlayerFlag> task) {
        PlayerFlag[] flags = PlayerFlag.values();
        for (int i = 0; i < 4; i++) {
            PlayerFlag flag = flags[i];
            if (players.containsKey(flag)) {
                task.accept(flag);
            }
        }
    }

    /**
     * Check if the specific flag won the game
     * @param flag: the specific {@link PlayerFlag}.
     * @return true if all pieces of the specific flag won, else false
     */
    public boolean isWon(PlayerFlag flag) {
        return getProcessingPieces(flag).isEmpty();
    }

    /**
     * Get playing player count (number of players who is not won yet)
     *
     */
    public int getPlayingCount() {
        AtomicInteger re = new AtomicInteger();
        forEachFlag(flag -> {
            if (!isWon(flag)) {
                re.getAndIncrement();
            }
        });
        return re.get();
    }

    /**
     * Get processing (non-won) pieces for specific flag.
     * @param flag: {@link PlayerFlag} that want to get.
     * @return Set included all processing pieces with specific flag.
     */
    @NonNull
    public Set<Piece> getProcessingPieces(PlayerFlag flag) {
        Set<Piece> re = new HashSet<>();
        for (Piece each : Objects.requireNonNull(pieces.get(flag), "The specific flag does not exist in this ChessBoard: "+flag.name())) {
            if (!each.isWon()) {
                re.add(each);
            }
        }
        return re;
    }

    /**
     * Get all pieces for specific flag.
     * @param flag: the specific {@link PlayerFlag}.
     * @return the set of all {@link Piece} that own by this flag.
     */
    @Nullable
    public Set<Piece> getPieces(PlayerFlag flag) {
        return pieces.get(flag);
    }

    public void setPieceToSlot(Piece p, Slot target) {
        Slot previous = p.getCurrentSlot();
        if (previous != null) {
            previous.removePiece(p);
        }
        target.addPiece(p);
        p.setCurrentSlot(target);
    }

    @NonNull
    public Slots getSlots() {
        return slots;
    }

    public Player getPlayer(PlayerFlag flag) {
        return players.get(flag);
    }

}
