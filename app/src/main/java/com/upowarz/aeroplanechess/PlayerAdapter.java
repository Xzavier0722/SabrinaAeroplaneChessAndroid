package com.upowarz.aeroplanechess;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
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

    public void addPlayer(){

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
        public Spinner spColorChoice;
        public TextView tvUserID;
        public Button mBtnKick;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PlayerViewHolder holder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.layout_list_player, null);
            holder = new PlayerViewHolder();
            holder.spColorChoice=convertView.findViewById(R.id.spColor);
            holder.tvUserID=convertView.findViewById(R.id.tv_useID);
            holder.mBtnKick=convertView.findViewById(R.id.btnKick);
            convertView.setTag(holder);
        }else{
           holder=(PlayerViewHolder)convertView.getTag();
        }

        String name = numplayer.get(position);
        holder.tvUserID.setText(name);

        String[]numColor = new String[]{"Red","Yellow","Blue","Green"};
        ArrayAdapter<String> colorAdapter = new ArrayAdapter<String>(mContext,R.layout.spinner_text_item,numColor);
        colorAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        holder.spColorChoice.setAdapter(colorAdapter);


        holder.mBtnKick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numplayer.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}