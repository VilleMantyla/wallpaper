package com.lucid.wallpapercreator;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 *
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
     * Changes the shape and background color when user touches this view.
     * Colors are chosen randomly because there is no UI for user to choose
     * the colors.
     */
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //Change the background color to a random color
                myRenderer.setBackgroundColor(ColorHelper.randomColor());
                //Change the shape color to a random color
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