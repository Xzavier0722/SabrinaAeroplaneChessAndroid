package com.xzavier0722.uon.sabrinaaeroplanechess.android.objects;

import androidx.annotation.NonNull;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.Flagged;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.PlayerFlag;

public class Slot implements Flagged {

    private final PlayerFlag flag;

    public Slot(PlayerFlag flag) {
        this.flag = flag;
    }

    @Override
    public PlayerFlag getFlag() {
        return flag;
    }
}
