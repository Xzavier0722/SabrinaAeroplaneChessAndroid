package com.upowarz.aeroplanechess;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import java.lang.reflect.Field;

public class DiceImage extends ImageView {

    
    public DiceImage(Context context,int i) {
        super(context);
        try {
            Field field=R.drawable.class.getField("d"+Integer.toString(i));
            setBackground(getResources().getDrawable(field.getInt(field)));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

}
