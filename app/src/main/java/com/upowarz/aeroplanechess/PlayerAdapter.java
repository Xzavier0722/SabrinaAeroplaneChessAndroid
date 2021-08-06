package com.upowarz.aeroplanechess;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.Sabrina;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.remote.RemoteController;
import com.xzavier0722.uon.sabrinaaeroplanechess.common.Utils;
import com.xzavier0722.uon.sabrinaaeroplanechess.common.game.PlayerProfile;
import com.xzavier0722.uon.sabrinaaeroplanechess.common.networking.Packet;
import com.xzavier0722.uon.sabrinaaeroplanechess.common.networking.Request;

import java.util.HashMap;
import java.util.List;

public class  PlayerAdapter extends BaseAdapter {

    private Context mContext;
    private List<PlayerProfile> numplayer;
    private LayoutInflater mLayoutInflater;
    private boolean right;

    public PlayerAdapter(Context context, List<PlayerProfile> objects, boolean roomOwner) {
        this.mContext = context;
        this.numplayer=objects;
        this.right=roomOwner;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void addPlayer(PlayerProfile playerProfile){
        numplayer.add(playerProfile);
        notifyDataSetChanged();
    }

    public void deletePlayer(String uuid){
        for (PlayerProfile playerProfile:numplayer){
            if (playerProfile.getUuid().toString().equals(uuid)){
                numplayer.remove(playerProfile);
                break;
            }
        }
        notifyDataSetChanged();
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


        String name = numplayer.get(position).getName();
        holder.tvUserID.setText(name);

        String[]numColor = new String[]{"Red","Yellow","Blue","Green"};
        ArrayAdapter<String> colorAdapter = new ArrayAdapter<String>(mContext,R.layout.spinner_text_item,numColor);
        colorAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        holder.spColorChoice.setAdapter(colorAdapter);


        if(right && position != 0){
            holder.mBtnKick.setVisibility(View.VISIBLE);
        }else{
            holder.mBtnKick.setVisibility(View.INVISIBLE);
        }

        if(right){
            holder.spColorChoice.setVisibility(View.VISIBLE);
        }else{
            holder.spColorChoice.setVisibility(View.INVISIBLE);
        }

        holder.mBtnKick.setOnClickListener(v -> {
            try {
                String data = "kick,"+numplayer.get(position).getUuid();
                RemoteController rc = Sabrina.getRemoteController();
                Packet packet = new Packet();
                packet.setRequest(Request.GAME_ROOM);
                packet.setData(rc.getAes().encrypt(data));
                packet.setSign(Utils.getSign(data));
                new Thread(()-> rc.send(RemoteController.gameService, packet, -1)).start();
            } catch (Exception e) {
                e.printStackTrace();
            }

            numplayer.remove(position);
            notifyDataSetChanged();

        });

        return convertView;
    }
}