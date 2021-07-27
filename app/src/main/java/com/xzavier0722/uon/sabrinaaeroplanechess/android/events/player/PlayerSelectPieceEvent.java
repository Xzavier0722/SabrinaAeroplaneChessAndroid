package com.xzavier0722.uon.sabrinaaeroplanechess.android.events.player;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.Player;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.ChessBoard;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Piece;

import java.util.List;

public class PlayerSelectPieceEvent extends PlayerDiceEvent {

    private Piece selectedPiece;
    private final List<Piece> availablePieces;

    public PlayerSelectPieceEvent(ChessBoard chessBoard, Player player, int diceNum, List<Piece> availablePieces) {
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

    public List<Piece> getAvailablePieces() {
        return availablePieces;
    }
}
