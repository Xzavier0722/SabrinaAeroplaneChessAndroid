package com.xzavier0722.uon.sabrinaaeroplanechess.android.remote;

import com.xzavier0722.uon.sabrinaaeroplanechess.common.networking.HandlingDatagramPacket;
import com.xzavier0722.uon.sabrinaaeroplanechess.common.networking.InetPointInfo;
import com.xzavier0722.uon.sabrinaaeroplanechess.common.networking.Packet;
import com.xzavier0722.uon.sabrinaaeroplanechess.common.networking.Request;
import com.xzavier0722.uon.sabrinaaeroplanechess.common.networking.SocketPoint;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RemoteController {

    private final SocketPoint point;
    private final Map<String,HandlingDatagramPacket> incomingPackets = new HashMap<>();
    private final Map<String,HandlingDatagramPacket> outgoingPackets = new HashMap<>();

    private final Map<Integer, RequestLock> waitThreads = new HashMap<>();

    private volatile int seq = 0;

    public RemoteController() throws SocketException {
        point = new SocketPoint(this::onReceived);
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
            send(info, re);
        }

        // Logic

    }

    public void send(InetPointInfo info, Packet packet) {
        packet.setTimestamp(System.currentTimeMillis());
        HandlingDatagramPacket handlingPacket = HandlingDatagramPacket.getFor(packet);

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

    public void requestWithBlocking(Packet p, RequestLock lock) {
        waitThreads.put(setSeq(p), lock);
    }

    private synchronized int setSeq(Packet p) {
        int re = seq++;
        p.setSequence(re);
        p.setId(re);
        return re;
    }

}
