package com.xzavier0722.uon.sabrinaaeroplanechess.android.events.piece;

import androidx.annotation.NonNull;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.ChessBoard;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Piece;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Slot;

public class PieceMoveEvent extends PieceEvent {

    private final Slot currentSlot;
    private final int rawStep;
    private int bonusStep;

    public PieceMoveEvent(@NonNull ChessBoard chessBoard, Piece piece, int rawStep) {
        super(chessBoard, piece);
        this.currentSlot = piece.getCurrentSlot();
        this.rawStep = rawStep;
        this.bonusStep = 0;
    }

    @NonNull
    public Slot getCurrentSlot() {
        return currentSlot;
    }

    public int getRawStep() {
        return rawStep;
    }

    public void addBonusStep(int step) {
        this.bonusStep += step;
    }

    public void setBonusStep(int step) {
        this.bonusStep = step;
    }

    public int getBonusStep() {
        return bonusStep;
    }

    public int getTotalStep() {
        return rawStep+bonusStep;
    }
}
