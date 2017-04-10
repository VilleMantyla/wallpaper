package com.lucid.wallpapercreator;

import android.graphics.Point;

/**
 * Created by Ville on 8.4.2017.
 */

public final class StyleSelector {

    private static float[] someCrds = new float[]{
            -0.5f, -0.311004243f, 0.0f, // bottom left
            .0f,  0.622008459f, 0.0f, // top
            0.5f, -0.311004243f, 0.0f  // bottom right
    };

    public static Point screenSize;

    private static Mandelbrot mandelbrot;

    public static Wallpaper getNewStyle(String style) {

        Wallpaper wp;

        //TODO create only once!
        switch (style) {
            case "triangle":
                wp = new Triangle(someCrds, Colors.WHITE);
                break;
            case "sierpinski":
                wp = new SierpinskiTriangle(6, new float[]{0,0}, 0.95f, Colors.BLUE);
                break;
            case "mandelbrot":
                if(mandelbrot == null) {
                    mandelbrot = new Mandelbrot(screenSize);
                }
                wp = mandelbrot;
                break;
            default:
                wp = new Triangle(someCrds, Colors.RED);
                break;

        }

        return wp;
    }






}
