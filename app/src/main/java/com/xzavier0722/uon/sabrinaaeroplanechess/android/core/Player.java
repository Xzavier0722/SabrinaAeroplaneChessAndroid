package com.xzavier0722.uon.sabrinaaeroplanechess.android.core;

public class Player implements Flagged {

    private final PlayerFlag flag;
    private final String name;
    private final PlayerType type;
    private boolean robot;

    public Player(PlayerFlag flag, String name) {
        this(flag, name, PlayerType.LOCAL);
    }

    public Player(PlayerFlag flag, String name, PlayerType type) {
        this.flag = flag;
        this.name = name;
        this.type = type;
        this.robot = type == PlayerType.ROBOT;
    }

    @Override
    public PlayerFlag getFlag() {
        return flag;
    }

    public String getName() {
        return name;
    }

    public void setRobot(boolean robot) {
        this.robot = robot;
    }

    public boolean isRobot() {
        return robot;
    }

    public PlayerType getType() {
        return type;
    }
}
