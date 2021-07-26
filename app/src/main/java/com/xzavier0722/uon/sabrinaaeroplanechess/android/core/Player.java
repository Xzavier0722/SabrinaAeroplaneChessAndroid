package com.xzavier0722.uon.sabrinaaeroplanechess.android.core;

public class Player implements Flagged {

    private final PlayerFlag flag;
    private final String name;

    private boolean isRobot;

    public Player(PlayerFlag flag, String name) {
        this(flag, name, false);
    }

    public Player(PlayerFlag flag, String name, boolean isRobot) {
        this.flag = flag;
        this.name = name;
        this.isRobot = isRobot;
    }

    @Override
    public PlayerFlag getFlag() {
        return flag;
    }

    public String getName() {
        return name;
    }

    public void setRobot(boolean robot) {
        isRobot = robot;
    }

    public boolean isRobot() {
        return isRobot;
    }
}
