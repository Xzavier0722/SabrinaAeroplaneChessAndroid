package com.upowarz.aeroplane_chess;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class  PlayerAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> numplayer;
    private LayoutInflater mLayoutInflater;

    public PlayerAdapter(Context context,List<String>objects) {
        this.mContext = context;
        this.numplayer=objects;
        mLayoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return numplayer.size();
    }

    @Override
    public Object getItem(int position) {
        return numplayer.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class PlayerViewHolder {
        public ImageView imageView;
        public TextView tvUserID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PlayerViewHolder holder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.layout_list_player, null);
            holder = new PlayerViewHolder();
            holder.imageView =(ImageView) convertView.findViewById(R.id.ivRoomMember);
            holder.tvUserID=convertView.findViewById(R.id.tv_useID);
            convertView.setTag(holder);
        }else{
           holder=(PlayerViewHolder)convertView.getTag();
        }
        String name=numplayer.get(position);
        holder.tvUserID.setText(name);
        return convertView;
    }
}