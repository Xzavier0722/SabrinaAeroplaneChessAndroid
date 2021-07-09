package com.upowarz.aeroplane_chess;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class IconButton extends Button {

    public IconButton(Context context) {
        super(context);
        init(context);
    }

    public IconButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public IconButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public IconButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context){
        Typeface font=Typeface.createFromAsset(context.getAssets(),"iconfont.ttf");
        this.setTypeface(font);
    }
}
