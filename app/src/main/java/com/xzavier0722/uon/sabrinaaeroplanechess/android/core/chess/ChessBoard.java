package com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess;

import androidx.annotation.NonNull;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.Sabrina;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.Player;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.PlayerFlag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

public class ChessBoard {

    private final Map<PlayerFlag, Player> players = new HashMap<>();
    private final Map<PlayerFlag, List<Piece>> pieces = new HashMap<>();

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
            List<Piece> newPieces = new ArrayList<>();
            int baseId = flag.ordinal()*4;
            for (int i = 0; i < 4; i++) {
                Piece piece = new Piece(flag, baseId++);
                piece.setCurrentSlot(Sabrina.getSlots().getHomeSlots(flag).get(i));
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
     * @param flag
     * @return
     */
    public boolean isWon(PlayerFlag flag) {
        return getProcessingPieces(flag).isEmpty();
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

}
