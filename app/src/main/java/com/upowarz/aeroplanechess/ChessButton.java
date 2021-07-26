package com.upowarz.aeroplanechess;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.PlayerFlag;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.PieceFace;

import java.lang.reflect.Field;

public class ChessButton extends ImageButton {

    private final PlayerFlag flag;
    private final Drawable bg;
    private final Drawable selectedBg;

    public ChessButton(Context context, PlayerFlag flag) {
        super(context);
        this.flag = flag;
        try {
            Field f = R.drawable.class.getField(flag.name().toLowerCase());
            bg = getResources().getDrawable(f.getInt(f));
            f = R.drawable.class.getField("selected_"+flag.name().toLowerCase());
            selectedBg = getResources().getDrawable(f.getInt(f));
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("Exception thrown while loading the resource");
        }
        this.setBackground(bg);
    }

    /**
     * Set direction of chess
     * @param face: ??
     */
    public void setDirection(PieceFace face){
        if (getRotation() != face.getRotation()) {
            animate().rotation(face.getRotation());
        }
    }

    public void setEnable(boolean isEnabled) {
        super.setEnabled(isEnabled);
        this.setBackground(isEnabled ? selectedBg : bg);
    }

    private class OnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {

        }
    }
}
