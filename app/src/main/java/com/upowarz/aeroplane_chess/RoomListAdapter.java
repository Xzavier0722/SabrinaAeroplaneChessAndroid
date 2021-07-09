package com.upowarz.aeroplane_chess;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RoomListAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mRoomList;
    private LayoutInflater mLayoutInflater;

    public RoomListAdapter(Context context,List<String>roomList){
        mContext=context;
        mRoomList=roomList;
        mLayoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mRoomList.size();
    }

    @Override
    public Object getItem(int position) {
        return mRoomList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class RoomViewHolder {
        public ImageView imageView;
        public TextView tvOwnerID;
        public TextView tvRoomNum;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RoomViewHolder holder=null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.layout_list_room, null);
            holder = new RoomViewHolder();
            holder.imageView =(ImageView) convertView.findViewById(R.id.ivRoomOwner);
            holder.tvOwnerID=convertView.findViewById(R.id.tv_roomOwner);
            holder.tvRoomNum=convertView.findViewById(R.id.tv_roomNum);
            convertView.setTag(holder);
        }else{
            holder=(RoomViewHolder) convertView.getTag();
        }
        holder.tvOwnerID.setText(mRoomList.get(position));
        return convertView;
    }
}
