package com.upowarz.aeroplanechess;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.PlayerFlag;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.Piece;
import com.xzavier0722.uon.sabrinaaeroplanechess.android.core.chess.PieceFace;

import java.lang.reflect.Field;

public class ChessButton extends ImageButton {

    private final Piece piece;
    private final Drawable bg;
    private final Drawable selectedBg;

    public ChessButton(Context context, Piece piece) {
        super(context);
        this.piece = piece;
        PlayerFlag flag = piece.getFlag();
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

    public Piece getPiece(){return piece;}

    private class OnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {

        }
    }
}
