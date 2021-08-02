package com.xzavier0722.uon.sabrinaaeroplanechess.android.events.remote;

public class RemotePieceSelectedEvent extends RemoteEvent {

    private final int pieceId;

    public RemotePieceSelectedEvent(int pieceId) {
        this.pieceId = pieceId;
    }

    public int getPieceId() {
        return pieceId;
    }
}
