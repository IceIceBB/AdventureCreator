package com.example.lmont.adventurecreator;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by klaus_000 on 10/15/2016.
 */

public class GameLibraryLinearLayout extends LinearLayout {

    private float scale = GameLibraryPagerAdapter.BIG_SCALE;

    public GameLibraryLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameLibraryLinearLayout(Context context) {
        super(context);
    }

    public void setScaleBoth(float scale) {
        this.scale = scale;
        this.invalidate();    // If you want to see the scale every time you set
        // scale you need to have this line here,
        // invalidate() function will call onDraw(Canvas)
        // to redraw the view for you
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // The main mechanism to display scale animation, you can customize it
        // as your needs
        int w = this.getWidth();
        int h = this.getHeight();
        canvas.scale(scale, scale, w / 2, h / 2);

        super.onDraw(canvas);
    }
}
