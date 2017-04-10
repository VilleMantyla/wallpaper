package com.lucid.wallpapercreator;

import android.graphics.Point;

/**
 * Created by Ville on 7.4.2017.
 */

public interface Wallpaper {

    void draw(float[] mvpMatrix);

    void changeToRandomColor();

}
