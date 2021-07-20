package com.xzavier0722.uon.sabrinaaeroplanechess.android.core;

public abstract class Player implements Flagged{

    private final PlayerFlag flag;

    public Player(PlayerFlag flag) {
        this.flag = flag;
    }

    @Override
    public PlayerFlag getFlag() {
        return flag;
    }
}
