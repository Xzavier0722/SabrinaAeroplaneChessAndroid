package com.xzavier0722.uon.sabrinaaeroplanechess.android.events.player;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.Player;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.ChessBoard;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Piece;

public class PlayerSelectPieceEvent extends PlayerDiceEvent {

    private Piece selectedPiece;

    public PlayerSelectPieceEvent(ChessBoard chessBoard, Player player, int diceNum) {
        super(chessBoard, player, diceNum);
    }

    public void setSelectedPieceId(Piece selectedPiece) {
        this.selectedPiece = selectedPiece;
    }

    public Piece getSelectedPieceId() {
        return selectedPiece;
    }
}
