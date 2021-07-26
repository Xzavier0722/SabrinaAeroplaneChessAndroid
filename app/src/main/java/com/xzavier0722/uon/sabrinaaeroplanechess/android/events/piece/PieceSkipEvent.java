package com.xzavier0722.uon.sabrinaaeroplanechess.android.events.piece;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.ChessBoard;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Piece;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Slot;

public class PieceSkipEvent extends PieceFromToEvent{
    public PieceSkipEvent(ChessBoard chessBoard, Piece piece, Slot slot, Slot targetSlot) {
        super(chessBoard, piece, slot, targetSlot);
    }
}
