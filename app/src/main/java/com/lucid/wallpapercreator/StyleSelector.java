package com.lucid.wallpapercreator;

import javax.crypto.Cipher;

/**
 * Created by Ville on 8.4.2017.
 */

public final class StyleSelector {

    private static float[] someCrds = new float[]{
            -0.5f, -0.311004243f, 0.0f, // bottom left
            .0f,  0.622008459f, 0.0f, // top
            0.5f, -0.311004243f, 0.0f  // bottom right
    };

    public static SierpinskiTriangle sierpinski = new SierpinskiTriangle(6, new float[]{0,0}, 0.95f, Colors.BLUE);

    public static WallpaperStyle getNewStyle(String style) {
        if(style.equals("triangle")) {
            return new Triangle(someCrds, Colors.WHITE);
        }
        if(style.equals("sierpinski")) {
            return new SierpinskiTriangle(6, new float[]{0,0}, 0.95f, Colors.BLUE);
        }
        return new Triangle(someCrds, Colors.RED);
    }






}
