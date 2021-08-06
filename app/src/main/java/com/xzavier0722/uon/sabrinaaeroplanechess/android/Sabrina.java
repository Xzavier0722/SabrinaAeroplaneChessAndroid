package com.xzavier0722.uon.sabrinaaeroplanechess.android;

import com.upowarz.aeroplanechess.ListenerGameRoom;
import com.upowarz.aeroplanechess.ListenerChessBoard;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event.EventManager;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.listeners.ChessBoardMonitor;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.listeners.PieceListener;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.listeners.RemoteListener;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.listeners.RobotListener;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.remote.RemoteController;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Sabrina {

    private static final EventManager eventManager = new EventManager();
    private static RemoteController remoteController;

    static {
        try {
            remoteController = new RemoteController();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        new PieceListener();
        new RobotListener();
        new ChessBoardMonitor();
        new ListenerChessBoard();
        new RemoteListener();
        new ListenerGameRoom();
    }

    public static EventManager getEventManager() {
        return eventManager;
    }

    public static InetAddress getServerHost() {
        try {
            return InetAddress.getByName("192.168.1.115");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static RemoteController getRemoteController() {
        return remoteController;
    }

}
