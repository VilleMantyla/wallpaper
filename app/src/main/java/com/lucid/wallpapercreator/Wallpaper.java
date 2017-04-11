package com.lucid.wallpapercreator;

import android.graphics.Point;

/**
 * Every new wallpaper should implement this interface show
 * that the renderer can draw it by calling the draw function.
 */

public interface Wallpaper {

    void draw(float[] mvpMatrix);

    void changeColor(float[] c);

}
