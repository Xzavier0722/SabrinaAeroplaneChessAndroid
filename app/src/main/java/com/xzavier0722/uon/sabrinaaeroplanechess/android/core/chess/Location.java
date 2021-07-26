package com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess;

public class Location {

    private final int x;
    private final int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Location)) {
            return false;
        }
        Location other = (Location)obj;
        return this.x == other.getX() && this.y == other.getY();
    }

    @Override
    public int hashCode() {
        return x+2*y;
    }
}
