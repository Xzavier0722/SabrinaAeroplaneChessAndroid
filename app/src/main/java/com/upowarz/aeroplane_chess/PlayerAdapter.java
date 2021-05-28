package com.upowarz.aeroplane_chess;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class  PlayerAdapter extends BaseAdapter {

    public void setNumplayer(List<Integer> numplayer) {
        this.numplayer = numplayer;
    }

    List<Integer>numplayer;
    @Override
    public int getCount() {
        return numplayer.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }


}
