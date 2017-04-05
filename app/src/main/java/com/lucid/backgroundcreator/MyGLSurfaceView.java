package com.lucid.backgroundcreator;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

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

        screenSize = new Point();
        if (!isInEditMode()) {
            Activity activity = (Activity) context;
            activity.getWindowManager().getDefaultDisplay().getRealSize(screenSize);
        }
        myRenderer = new MyRenderer(screenSize);

        setRenderer(myRenderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        //float x = e.getX();
        //float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                myRenderer.setRandomColor(randomColor());

                requestRender();
        }

        return true;
    }

    //Debug
    public float[] randomColor() {
        float red = (float)Math.random();
        float green = (float)Math.random();
        float blue = (float)Math.random();
        return new float[]{red, green, blue};
    }

    public void setScreenSize(Point s) {
        screenSize = s;
    }

}