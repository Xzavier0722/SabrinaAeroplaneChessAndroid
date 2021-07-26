package com.xzavier0722.uon.sabrinaaeroplanechess.android.events.player;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.Player;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.ChessBoard;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Piece;

import java.util.Set;

public class PlayerSelectPieceEvent extends PlayerDiceEvent {

    private Piece selectedPiece;
    private final Set<Piece> availablePieces;

    public PlayerSelectPieceEvent(ChessBoard chessBoard, Player player, int diceNum, Set<Piece> availablePieces) {
        super(chessBoard, player, diceNum);
        this.availablePieces = availablePieces;
    }

    public void setSelectedPiece(Piece selectedPiece) {
        if (!availablePieces.contains(selectedPiece)) {
            throw new IllegalArgumentException("The selected piece must in the available set");
        }
        this.selectedPiece = selectedPiece;
    }

    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    public Set<Piece> getAvailablePieces() {
        return availablePieces;
    }
}
