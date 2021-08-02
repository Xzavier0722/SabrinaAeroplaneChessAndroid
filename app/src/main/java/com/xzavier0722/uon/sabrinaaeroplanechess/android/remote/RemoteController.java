package com.xzavier0722.uon.sabrinaaeroplanechess.android.remote;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.Sabrina;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.remote.GameRoomDestroyEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.remote.GameRoomJoinEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.remote.GameRoomKickEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.remote.GameRoomLeaveEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.remote.RemoteGameStartEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.remote.RemotePieceSelectedEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.events.remote.RemoteTurnStartEvent;
import com.xzavier0722.uon.sabrinaaeroplanechess.common.Utils;
import com.xzavier0722.uon.sabrinaaeroplanechess.common.game.PlayerProfile;
import com.xzavier0722.uon.sabrinaaeroplanechess.common.networking.HandlingDatagramPacket;
import com.xzavier0722.uon.sabrinaaeroplanechess.common.networking.InetPointInfo;
import com.xzavier0722.uon.sabrinaaeroplanechess.common.networking.Packet;
import com.xzavier0722.uon.sabrinaaeroplanechess.common.networking.Request;
import com.xzavier0722.uon.sabrinaaeroplanechess.common.networking.SocketPoint;
import com.xzavier0722.uon.sabrinaaeroplanechess.common.security.AES;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public class RemoteController {

    private final SocketPoint point;
    private final Map<String,HandlingDatagramPacket> incomingPackets = new HashMap<>();
    private final Map<String,HandlingDatagramPacket> outgoingPackets = new HashMap<>();

    public static final InetPointInfo loginService = new InetPointInfo(Sabrina.getServerHost(), 7220);
    public static final InetPointInfo gameService = new InetPointInfo(Sabrina.getServerHost(), 7221);

    private final Map<Integer, RequestLock> waitThreads = new HashMap<>();
    private AES aes;
    private String sessionId;

    private volatile int seq = 0;

    public RemoteController() throws SocketException {
        point = new SocketPoint(this::onReceived);
        point.start();
    }

    private void onReceived(DatagramPacket packet) {
        InetPointInfo info = InetPointInfo.get(packet);
        String infoStr = info.toString();
        HandlingDatagramPacket handlingPacket = incomingPackets.get(infoStr);
        byte[] data = packet.getData();

        if (handlingPacket == null) {
            // New income
            handlingPacket = new HandlingDatagramPacket();
            handlingPacket.accept(data);
            byte identifier = data[0];
            if (identifier == 0x48) {
                int len = (int)data[1];
                if (len == 1) {
                    onReceive(info ,handlingPacket.getPacket());
                    return;
                }
            }
            incomingPackets.put(infoStr, handlingPacket);
        } else {
            handlingPacket.accept(data);
            if (handlingPacket.isCompleted()) {
                onReceive(info, handlingPacket.getPacket());
            }
        }
    }

    private void onReceive(InetPointInfo info, Packet p) {
        Request request = p.getRequest();
        switch (request) {
            case CONFIRM:
                outgoingPackets.remove(info.toString());
                return;
            case RESEND:
                HandlingDatagramPacket handlingPacket = outgoingPackets.get(info.toString());
                if (handlingPacket != null) {
                    Optional<DatagramPacket> rePacket = handlingPacket.getDatagramPacket(Integer.parseInt(p.getData()), info);
                    if (rePacket.isPresent()) {
                        try {
                            point.send(rePacket.get());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return;
        }
        incomingPackets.remove(info.toString());
        // Send confirm
        if (request.requireConfirm()) {
            Packet re = new Packet();
            re.setData("CONFIRM");
            re.setRequest(Request.CONFIRM);
            re.setSign("NULL");
            re.setSessionId(p.getSessionId());
            send(info, re, -1);
        }

        // Logic

        // Process blocking requests
        RequestLock lock = waitThreads.remove(p.getId());
        if (lock != null) {
            try {

                switch (p.getRequest()) {
                    case ERROR:
                    case REGISTER:
                        lock.setValue(p.getData());
                        break;
                    case LOGIN:
                        sessionId = p.getSessionId();
                    default:
                        String data = aes.decrypt(p.getData());
                        lock.setValue(verifySign(p, data) ? data : "ERROR");
                }
                lock.notifyAll();
            } catch (IllegalBlockSizeException | BadPaddingException e) {
                e.printStackTrace();
            }
            return;
        }

        // Other processes
        try {
            if (!verifySign(p)) {
                return;
            }

            String[] requestStr = aes.decrypt(p.getData()).split(",");

            switch (p.getRequest()) {
                case GAME_ROOM:
                    if (requestStr[0].equals("kick")) {
                        if (requestStr.length == 1) {
                            Sabrina.getEventManager().callListener(new GameRoomDestroyEvent());
                        } else {
                            Sabrina.getEventManager().callListener(new GameRoomKickEvent(requestStr[1]));
                        }
                        return;
                    }
                    PlayerProfile profile = Utils.getGson().fromJson(requestStr[1], PlayerProfile.class);
                    switch (requestStr[0]) {
                        case "remove":
                            Sabrina.getEventManager().callListener(new GameRoomLeaveEvent(profile));
                            return;
                        case "add":
                            Sabrina.getEventManager().callListener(new GameRoomJoinEvent(profile));
                            return;
                        case "start":
                            Sabrina.getEventManager().callListener(new RemoteGameStartEvent(new ArrayList<>(Arrays.asList(requestStr).subList(1, requestStr.length))));
                            return;
                    }
                    return;
                case GAME_PROCESS:
                    switch (requestStr[0]) {
                        case "turnStart":
                            Sabrina.getEventManager().callListener(new RemoteTurnStartEvent(Integer.parseInt(requestStr[1])));
                            return;
                        case "pieceSelected":
                            Sabrina.getEventManager().callListener(new RemotePieceSelectedEvent(Integer.parseInt(requestStr[1])));
                            return;
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PlayerProfile login(String name, String password) {
        try {
            byte[] key = Utils.sha256(password);
            aes = new AES(key);
            Packet p = new Packet();
            p.setSessionId(Utils.base64(name));
            p.setData(aes.encrypt("Login"));
            p.setSign("NULL");
            p.setRequest(Request.LOGIN);

            String response = requestWithBlocking(loginService,p).getValue();
            if (response != null && !response.equals("ERROR")) {
                String[] responseArr = response.split(",");
                aes = new AES(responseArr[0]);
                return Utils.getGson().fromJson(responseArr[1], PlayerProfile.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void send(InetPointInfo info, Packet packet, long timestamp) {
        packet.setTimestamp(timestamp == -1 ? System.currentTimeMillis() : timestamp);
        HandlingDatagramPacket handlingPacket = HandlingDatagramPacket.getFor(packet);

        if (packet.getSessionId() == null) {
            packet.setSessionId(sessionId == null ? "NULL" : sessionId);
        }

        for (int i = 0; i < handlingPacket.getSliceCount(); i++) {
            try {
                point.send(handlingPacket.getDatagramPacket(i, info).get());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (packet.getRequest().requireConfirm()) {
            outgoingPackets.put(info.toString(),handlingPacket);
        }
    }

    public RequestLock requestWithBlocking(InetPointInfo info, Packet p) {
        RequestLock lock = new RequestLock();
        waitThreads.put(setSeq(p), lock);
        send(info, p, p.getTimestamp());
        try {
            lock.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return lock;
    }

    public AES getAes() {
        return aes;
    }

    private synchronized int setSeq(Packet p) {
        int re = seq++;
        p.setSequence(re);
        p.setId(re);
        return re;
    }

    private boolean verifySign(Packet p) throws BadPaddingException, IllegalBlockSizeException {
        return verifySign(p, aes.decrypt(p.getData()));
    }

    private boolean verifySign(Packet p, String plainData) {
        return p.getSign().equals(Utils.getSign(plainData));
    }

}
