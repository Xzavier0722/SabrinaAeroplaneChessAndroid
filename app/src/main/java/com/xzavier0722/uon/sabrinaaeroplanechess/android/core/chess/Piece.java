package com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.Flagged;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.PlayerFlag;

public class Piece implements Flagged {

    private final PlayerFlag flag;
    private final int id;
    private boolean won;
    private Slot currentSlot;
    private Slot homeSlot;

    public Piece(PlayerFlag flag, int id) {
        this.flag = flag;
        this.id = id;
        won = false;
    }


    public Slot getHomeSlot() {
        return homeSlot;
    }

    public void setHomeSlot(Slot homeSlot) {
        this.homeSlot = homeSlot;
    }

    /**
     * Get current slot that this piece in.
     * @return the {@link Slot} object now this piece in.
     */
    public Slot getCurrentSlot() {
        return currentSlot;
    }

    /**
     * Set slot that piece in.
     * @param currentSlot: the slot will be move to.
     */
    public void setCurrentSlot(Slot currentSlot) {
        this.currentSlot = currentSlot;
    }

    /**
     * Call when this piece reached the destination.
     */
    public void setWon(){
        won = true;
    }

    /**
     * Get piece win status.
     * @return true if won, false if not.
     */
    public boolean isWon(){
        return won;
    }

    /**
     * Get piece id in chessboard.
     * @return int id.
     */
    public int getId(){
        return id;
    }

    @Override
    public PlayerFlag getFlag() {
        return flag;
    }
}
