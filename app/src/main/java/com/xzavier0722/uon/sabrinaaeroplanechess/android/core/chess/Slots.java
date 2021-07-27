package com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.PlayerFlag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Slots {
    private final List<Slot> slots = new ArrayList<>();
    private final Map<PlayerFlag, List<Slot>> privateSlots = new HashMap<>();
    private final Map<PlayerFlag, List<Slot>> homeSlots = new HashMap<>();
    private final Map<PlayerFlag, Slot> prepareSlots = new HashMap<>();
    private final Map<PlayerFlag, Integer> firstSlotIndex = new HashMap<>();

    private PlayerFlag nextFlag;
    private PieceFace nextFace;

    public Slots() {
        init();
    }

    private void init() {
        firstSlotIndex.put(PlayerFlag.YELLOW, 0);
        firstSlotIndex.put(PlayerFlag.BLUE, 13);
        firstSlotIndex.put(PlayerFlag.GREEN, 26);
        firstSlotIndex.put(PlayerFlag.RED, 39);
        //init public slots
        nextFlag = PlayerFlag.RED;
        nextFace = PieceFace.LEFT;
        slots.add(createPublicSlot(31,23));
        slots.add(createPublicSlot(29,24));
        slots.add(createPublicSlot(27,24));
        slots.add(createPublicSlot(25,23));
        nextFace = PieceFace.DOWN;
        slots.add(createPublicSlot(23,25));
        slots.add(createPublicSlot(24,27));
        slots.add(createPublicSlot(24,29));
        nextFace = PieceFace.LEFT;
        slots.add(createPublicSlot(23,31));
        slots.add(createPublicSlot(21,32));
        slots.add(createPublicSlot(19,32));
        slots.add(createPublicSlot(17,32));
        slots.add(createPublicSlot(15,32));
        slots.add(createPublicSlot(13,32));
        nextFace = PieceFace.UP;
        slots.add(createPublicSlot(11,31));
        slots.add(createPublicSlot(10,29));
        slots.add(createPublicSlot(10,27));
        slots.add(createPublicSlot(11,25));
        nextFace = PieceFace.LEFT;
        slots.add(createPublicSlot(9,23));
        slots.add(createPublicSlot(7,24));
        slots.add(createPublicSlot(5,24));
        nextFace = PieceFace.UP;
        slots.add(createPublicSlot(3,23));
        slots.add(createPublicSlot(2,21));
        slots.add(createPublicSlot(2,19));
        slots.add(createPublicSlot(2,17));
        slots.add(createPublicSlot(2,15));
        slots.add(createPublicSlot(2,13));
        nextFace = PieceFace.RIGHT;
        slots.add(createPublicSlot(3,11));
        slots.add(createPublicSlot(5,10));
        slots.add(createPublicSlot(7,10));
        slots.add(createPublicSlot(9,11));
        nextFace = PieceFace.UP;
        slots.add(createPublicSlot(11,9));
        slots.add(createPublicSlot(10,7));
        slots.add(createPublicSlot(10,5));
        nextFace = PieceFace.RIGHT;
        slots.add(createPublicSlot(11,3));
        slots.add(createPublicSlot(13,2));
        slots.add(createPublicSlot(15,2));
        slots.add(createPublicSlot(17,2));
        slots.add(createPublicSlot(19,2));
        slots.add(createPublicSlot(21,2));
        nextFace = PieceFace.DOWN;
        slots.add(createPublicSlot(23,3));
        slots.add(createPublicSlot(24,5));
        slots.add(createPublicSlot(24,7));
        slots.add(createPublicSlot(23,9));
        nextFace = PieceFace.RIGHT;
        slots.add(createPublicSlot(25,11));
        slots.add(createPublicSlot(27,10));
        slots.add(createPublicSlot(29,10));
        nextFace = PieceFace.DOWN;
        slots.add(createPublicSlot(31,11));
        slots.add(createPublicSlot(32,13));
        slots.add(createPublicSlot(32,15));
        slots.add(createPublicSlot(32,17));
        slots.add(createPublicSlot(32,19));
        slots.add(createPublicSlot(32,21));

        //init private slots
        List<Slot> yellow = new LinkedList<>();
        yellow.add(new Slot(PlayerFlag.YELLOW,new Location(29,17),SlotType.PRIVATE_SLOT,PieceFace.LEFT));
        yellow.add(new Slot(PlayerFlag.YELLOW,new Location(27,17),SlotType.PRIVATE_SLOT,PieceFace.LEFT));
        yellow.add(new Slot(PlayerFlag.YELLOW,new Location(25,17),SlotType.PRIVATE_SLOT,PieceFace.LEFT));
        yellow.add(new Slot(PlayerFlag.YELLOW,new Location(23,17),SlotType.PRIVATE_SLOT,PieceFace.LEFT));
        yellow.add(new Slot(PlayerFlag.YELLOW,new Location(21,17),SlotType.PRIVATE_SLOT,PieceFace.LEFT));
        yellow.add(new Slot(PlayerFlag.YELLOW,new Location(19,17),SlotType.TARGET_SLOT,PieceFace.LEFT));
        privateSlots.put(PlayerFlag.YELLOW,yellow);

        List<Slot> blue = new LinkedList<>();
        blue.add(new Slot(PlayerFlag.BLUE,new Location(17,29),SlotType.PRIVATE_SLOT,PieceFace.UP));
        blue.add(new Slot(PlayerFlag.BLUE,new Location(17,27),SlotType.PRIVATE_SLOT,PieceFace.UP));
        blue.add(new Slot(PlayerFlag.BLUE,new Location(17,25),SlotType.PRIVATE_SLOT,PieceFace.UP));
        blue.add(new Slot(PlayerFlag.BLUE,new Location(17,23),SlotType.PRIVATE_SLOT,PieceFace.UP));
        blue.add(new Slot(PlayerFlag.BLUE,new Location(17,21),SlotType.PRIVATE_SLOT,PieceFace.UP));
        blue.add(new Slot(PlayerFlag.BLUE,new Location(17,19),SlotType.TARGET_SLOT,PieceFace.UP));
        privateSlots.put(PlayerFlag.BLUE,blue);

        List<Slot> green = new LinkedList<>();
        green.add(new Slot(PlayerFlag.GREEN,new Location(5,17),SlotType.PRIVATE_SLOT,PieceFace.RIGHT));
        green.add(new Slot(PlayerFlag.GREEN,new Location(7,17),SlotType.PRIVATE_SLOT,PieceFace.RIGHT));
        green.add(new Slot(PlayerFlag.GREEN,new Location(9,17),SlotType.PRIVATE_SLOT,PieceFace.RIGHT));
        green.add(new Slot(PlayerFlag.GREEN,new Location(11,17),SlotType.PRIVATE_SLOT,PieceFace.RIGHT));
        green.add(new Slot(PlayerFlag.GREEN,new Location(13,17),SlotType.PRIVATE_SLOT,PieceFace.RIGHT));
        green.add(new Slot(PlayerFlag.GREEN,new Location(15,17),SlotType.TARGET_SLOT,PieceFace.RIGHT));
        privateSlots.put(PlayerFlag.GREEN,green);

        List<Slot> red = new LinkedList<>();
        red.add(new Slot(PlayerFlag.RED,new Location(17,5),SlotType.PRIVATE_SLOT,PieceFace.DOWN));
        red.add(new Slot(PlayerFlag.RED,new Location(17,7),SlotType.PRIVATE_SLOT,PieceFace.DOWN));
        red.add(new Slot(PlayerFlag.RED,new Location(17,9),SlotType.PRIVATE_SLOT,PieceFace.DOWN));
        red.add(new Slot(PlayerFlag.RED,new Location(17,11),SlotType.PRIVATE_SLOT,PieceFace.DOWN));
        red.add(new Slot(PlayerFlag.RED,new Location(17,13),SlotType.PRIVATE_SLOT,PieceFace.DOWN));
        red.add(new Slot(PlayerFlag.RED,new Location(17,15),SlotType.TARGET_SLOT,PieceFace.DOWN));
        privateSlots.put(PlayerFlag.RED,red);

        //init home slots
        List<Slot> yellowHome = new LinkedList<>();
        yellowHome.add(new Slot(PlayerFlag.YELLOW,new Location(29,29),SlotType.HOME_SLOT,PieceFace.LEFT));
        yellowHome.add(new Slot(PlayerFlag.YELLOW,new Location(31,29),SlotType.HOME_SLOT,PieceFace.LEFT));
        yellowHome.add(new Slot(PlayerFlag.YELLOW,new Location(29,31),SlotType.HOME_SLOT,PieceFace.LEFT));
        yellowHome.add(new Slot(PlayerFlag.YELLOW,new Location(31,31),SlotType.HOME_SLOT,PieceFace.LEFT));
        homeSlots.put(PlayerFlag.YELLOW,yellowHome);

        List<Slot> blueHome = new LinkedList<>();
        blueHome.add(new Slot(PlayerFlag.BLUE,new Location(3,29),SlotType.HOME_SLOT,PieceFace.UP));
        blueHome.add(new Slot(PlayerFlag.BLUE,new Location(5,29),SlotType.HOME_SLOT,PieceFace.UP));
        blueHome.add(new Slot(PlayerFlag.BLUE,new Location(3,31),SlotType.HOME_SLOT,PieceFace.UP));
        blueHome.add(new Slot(PlayerFlag.BLUE,new Location(5,31),SlotType.HOME_SLOT,PieceFace.UP));
        homeSlots.put(PlayerFlag.BLUE,blueHome);

        List<Slot> greenHome = new LinkedList<>();
        greenHome.add(new Slot(PlayerFlag.GREEN,new Location(3,3),SlotType.HOME_SLOT,PieceFace.RIGHT));
        greenHome.add(new Slot(PlayerFlag.GREEN,new Location(5,3),SlotType.HOME_SLOT,PieceFace.RIGHT));
        greenHome.add(new Slot(PlayerFlag.GREEN,new Location(3,5),SlotType.HOME_SLOT,PieceFace.RIGHT));
        greenHome.add(new Slot(PlayerFlag.GREEN,new Location(5,5),SlotType.HOME_SLOT,PieceFace.RIGHT));
        homeSlots.put(PlayerFlag.GREEN,greenHome);

        List<Slot> redHome = new LinkedList<>();
        redHome.add(new Slot(PlayerFlag.RED,new Location(29,3),SlotType.HOME_SLOT,PieceFace.DOWN));
        redHome.add(new Slot(PlayerFlag.RED,new Location(31,3),SlotType.HOME_SLOT,PieceFace.DOWN));
        redHome.add(new Slot(PlayerFlag.RED,new Location(29,5),SlotType.HOME_SLOT,PieceFace.DOWN));
        redHome.add(new Slot(PlayerFlag.RED,new Location(31,5),SlotType.HOME_SLOT,PieceFace.DOWN));
        homeSlots.put(PlayerFlag.RED,redHome);

        //init start slots
        prepareSlots.put(PlayerFlag.YELLOW,new Slot(PlayerFlag.YELLOW,new Location(33,25),SlotType.START_SLOT,PieceFace.LEFT));
        prepareSlots.put(PlayerFlag.BLUE,new Slot(PlayerFlag.BLUE,new Location(9,33),SlotType.START_SLOT,PieceFace.UP));
        prepareSlots.put(PlayerFlag.GREEN,new Slot(PlayerFlag.GREEN,new Location(1,9),SlotType.START_SLOT,PieceFace.RIGHT));
        prepareSlots.put(PlayerFlag.RED,new Slot(PlayerFlag.YELLOW,new Location(25,1),SlotType.START_SLOT,PieceFace.DOWN));

        //set special slots
        slots.get(10).setLast(true);
        slots.get(23).setLast(true);
        slots.get(36).setLast(true);
        slots.get(49).setLast(true);
        slots.get(4).setOnTrack(true);
        slots.get(17).setOnTrack(true);
        slots.get(30).setOnTrack(true);
        slots.get(43).setOnTrack(true);

        // Set ordinal
        for (int i = 0; i < slots.size(); i++) {
            Slot each = slots.get(i);
            each.setOrdinal(i);
        }
        for (List<Slot> eachPrivateSlots : privateSlots.values()) {
            for (int i = 0; i < eachPrivateSlots.size(); i++) {
                Slot each = eachPrivateSlots.get(i);
                each.setOrdinal(i);
            }
        }
    }

    private Slot createPublicSlot(int x, int y){
        Slot re = new Slot(nextFlag,new Location(x ,y),SlotType.PUBLIC_SLOT,nextFace);
        nextFlag = PlayerFlag.values()[nextFlag.ordinal()==3?0:nextFlag.ordinal()+1];
        return re;
    }

    @NonNull
    public Slot getPrepareSlot(PlayerFlag flag) {
        return prepareSlots.get(flag);
    }

    @NonNull
    public List<Slot> getPublicSlots() {
        return slots;
    }

    @NonNull
    public List<Slot> getPrivateSlots(PlayerFlag flag) {
        return privateSlots.get(flag);
    }

    @NonNull
    public List<Slot> getHomeSlots(PlayerFlag flag) {
        return homeSlots.get(flag);
    }

    public Slot getStartSlot(PlayerFlag flag) {
        return slots.get(firstSlotIndex.get(flag));
    }

    /**
     * Get the slot after specific step for specific flag
     * @param currentSlot: the current slot
     * @param num: specific number of step after
     * @return the {@link Slot} after num step
     */
    @NonNull
    public Slot getPublicSlotAfter(Slot currentSlot, int num) {
        if (currentSlot.getType() != SlotType.PUBLIC_SLOT) {
            throw new IllegalArgumentException("The current slot must be a public slot");
        }
        if (num < 1 || num >= slots.size()) {
            throw new IllegalArgumentException("The step number must between 1 - "+slots.size());
        }

        int targetSlotIndex = currentSlot.ordinal() + num;
        if (targetSlotIndex >= slots.size()) {
            targetSlotIndex = slots.size() - targetSlotIndex;
        }
        return slots.get(targetSlotIndex);
    }

    /**
     * Get next slot for specific flag
     * @param flag: the flag of piece
     * @param currentSlot: the current slot
     * @return the slot next to the current slot, or null if is the last
     */
    @Nullable
    public Slot getNext(PlayerFlag flag, Slot currentSlot) {
        switch (currentSlot.getType()) {
            case HOME_SLOT:
                return prepareSlots.get(flag);
            case START_SLOT:
                return getStartSlot(flag);
            case PRIVATE_SLOT:
                return privateSlots.get(flag).get(currentSlot.ordinal()+1);
            case PUBLIC_SLOT:
                return (currentSlot.getFlag() == flag && currentSlot.isLast()) ? privateSlots.get(flag).get(0) : getPublicSlotAfter(currentSlot, 1);
            default:
                return null;
        }
    }

}
