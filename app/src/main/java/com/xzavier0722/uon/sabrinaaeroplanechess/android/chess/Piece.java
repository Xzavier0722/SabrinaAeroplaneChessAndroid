package com.xzavier0722.uon.sabrinaaeroplanechess.android.chess;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.Flagged;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.PlayerFlag;

public class Piece implements Flagged {

    private final PlayerFlag flag;
    private boolean won;
    private boolean enabled;
    private int id;
    private Slot currentSlot;

    public Piece(PlayerFlag flag) {
        this.flag = flag;
        won = false;
        enabled = false;
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
    public void onWin(){
        won = true;
        enabled = false;
    }

    /**
     * Get piece win status.
     * @return true if won, false if not.
     */
    public boolean isWon(){
        return won;
    }

    /**
     * Reset win status.
     */
    public void reset(){
        won = false;
        currentSlot = null;
    }

    /**
     * Set piece enable status. This design is for UI to update the piece status.
     * When set to true, this will check if hit the requirement with the dice number and slot type.
     * If not, will skip setting.
     * @param enabled: boolean that enable or not.
     * @param diceNum: the dice num rolled. If set to false, this will be ignored.
     */
    public void setEnabled(boolean enabled, int diceNum){
        if(!enabled || currentSlot.getType()!=SlotType.HOME_SLOT || diceNum >= 5){
            this.enabled = enabled;
        }
    }

    /**
     * Get the enabled status. This design is for UI to update the piece status.
     * @return true if enabled, false if not.
     */
    public boolean isEnabled(){
        return enabled;
    }

    /**
     * Set piece id in the chessboard. Usually is the load order.
     * @param id: int id.
     */
    public void setId(int id){
        this.id = id;
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
