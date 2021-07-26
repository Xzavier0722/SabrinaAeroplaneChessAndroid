package com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess;

public enum PieceFace {

    UP(0),
    RIGHT(90),
    DOWN(180),
    LEFT(270);

    private final int rotation;

    PieceFace(int rotation) {
        this.rotation = rotation;
    }

    public int getRotation() {
        return rotation;
    }

}
