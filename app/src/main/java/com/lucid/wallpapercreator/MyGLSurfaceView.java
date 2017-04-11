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

    public MyRenderer getmRenderer()
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
            //Initialise StyleSelector
            StyleSelector.screenSize = screenSize;
        }

        myRenderer = new MyRenderer(screenSize);

        setRenderer(myRenderer);

        // Render the view only when there is a change in the drawing data
        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        //float x = e.getX();
        //float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //TODO implement menu logic
                myRenderer.setBackgroundColor(ColorHelper.randomColor()); //Background color
                myRenderer.changeWpstylecolor(ColorHelper.randomColor()); //Shape color
        }

        return true;
    }

    public Point getScreenSize() {
        return screenSize;
    }

    public void createWallpaper() {
        myRenderer.createWallpaper();
        //requestRender();
    }

}