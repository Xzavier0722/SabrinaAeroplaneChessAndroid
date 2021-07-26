package com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.Flagged;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.PlayerFlag;

import java.util.ArrayList;
import java.util.List;

public class Slot implements Flagged {

    private final PlayerFlag flag;
    private final List<Piece> pieces;
    private final SlotType type;
    private final PieceFace face;
    private final Location loc;
    private boolean last;
    private boolean onTrack;

    public Slot(PlayerFlag flag, Location loc, SlotType type, PieceFace face) {
        this.flag = flag;
        this.loc = loc;
        pieces = new ArrayList<>();
        this.type = type;
        this.face = face;
        last = false;
        onTrack = false;
    }

    /**
     * Get occupied status.
     * @return true if there more than one piece in this slot. Else false.
     */
    public boolean isOccupied() {
        return pieces.size()>0;
    }

    /**
     * Get the occupied piece player flag.
     * @return the piece {@link PlayerFlag} that current occupied.
     */
    public PlayerFlag getOccupiedFlag() {
        return isOccupied()?pieces.get(0).getFlag():null;
    }

    /**
     * Add a piece into slot. Since the game rule, the slot cannot occupy by different flag of piece.
     * Check it before call to prevent from exception throwing.
     * @param piece: the piece will add in.
     * @throws IllegalArgumentException if the slot occupied by another flag of pieces.
     */
    public void addPiece(Piece piece) {
        if(isOccupied()&&getOccupiedFlag()!=piece.getFlag()) throw new IllegalArgumentException("Slot cannot be occupied by different flag.");
        pieces.add(piece);
    }

    /**
     * Get slot type.
     * @return the {@link SlotType} that this slot is.
     */
    public SlotType getType() {
        return type;
    }

    /**
     * Remove a piece from this slot.
     * @param piece: the piece will be removed.
     */
    public void removePiece(Piece piece) {
        pieces.remove(piece);
    }

    /**
     * Get facing direction. This design for UI to set the piece direction when update.
     * @return the {@link PieceFace} that this slot face.
     */
    public PieceFace getFace() {
        return face;
    }

    /**
     * Get slot location. This design for UI to know the slot location for updating.
     * @return the {@link Location} where this slot is.
     */
    public Location getLocation() {
        return loc;
    }

    /**
     * Set if the slot is the last for current player flag. Default is false.
     * @param last: the boolean will be set to.
     */
    public void setLast(boolean last) {
        this.last = last;
    }

    /**
     * Check if the slot is the last for current player flag.
     * @return true if is the last, false if not.
     */
    public boolean isLast() {
        return last;
    }

    /**
     * Get if the slot will track the piece which with same flag to other side.
     * @return true if is tracking slot, false if not.
     */
    public boolean isOnTrack() {
        return onTrack;
    }

    /**
     * Set if the slot will track the piece with same flag to other side. Default is false.
     * @param onTrack: boolean will be set to.
     */
    public void setOnTrack(boolean onTrack) {
        this.onTrack = onTrack;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    @Override
    public PlayerFlag getFlag() {
        return flag;
    }
}
