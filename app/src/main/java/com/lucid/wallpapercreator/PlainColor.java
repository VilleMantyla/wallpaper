package com.lucid.wallpapercreator;

import android.opengl.GLES20;

/**
 * Created by Ville on 11.4.2017.
 */

public class PlainColor implements Wallpaper {

    private float[] color =  ColorHelper.randomColor();
    @Override
    public void draw(float[] mvpMatrix) {
        GLES20.glClearColor(color[0], color[1], color[2], 1f);
    }

    @Override
    public void changeColor(float[] c) {
        color = c;
    }
}
