package com.lucid.wallpapercreator;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Ville on 22.3.2017.
 */

public class MyGLSurfaceView extends GLSurfaceView {

    private final MyRenderer myRenderer;

    private Point screenSize;

    public MyRenderer getMyRenderer()
    {
        return myRenderer;
    }

    public MyGLSurfaceView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);

        setEGLContextClientVersion(2);

        if (!isInEditMode()) {
            screenSize = new Point();
            Activity activity = (Activity) context;
            activity.getWindowManager().getDefaultDisplay().getRealSize(screenSize);
            //Initialise PredefinedStyles
            PredefinedStyles.screenSize = screenSize;
        }

        myRenderer = new MyRenderer(screenSize);

        setRenderer(myRenderer);
    }

    /**
     * Changes the shape and background color (depending on the style)
     * when user touches this view.
     */
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //change colors of the current wallpaper style
                myRenderer.changeColors(ColorHelper.randomColor());
        }
        return true;
    }

    /**
     * Tells the renderer to create a wallpaper of the current frame.
     */
    public void createWallpaper() {
        myRenderer.createWallpaper();
    }

}