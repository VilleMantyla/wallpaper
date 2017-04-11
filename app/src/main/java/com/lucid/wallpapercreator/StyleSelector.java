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

    private static SierpinskiTriangle sierpinskiTriangle;

    public static Wallpaper getNewStyle(String style) {

        Wallpaper wp;

        //TODO create only once!
        switch (style) {
            case "triangle":
                return new Triangle(someCrds, Colors.randomColor());
                //break;
            case "sierpinski":
                return (sierpinskiTriangle == null) ? sierpinskiTriangle = new SierpinskiTriangle(6, new float[]{0,0}, 0.95f, Colors.randomColor()) : sierpinskiTriangle;
                //break;
            case "mandelbrot":
                //mandelbrot = (mandelbrot == null) ? new Mandelbrot(screenSize) : mandelbrot;
                return (mandelbrot == null) ? mandelbrot = new Mandelbrot(screenSize) : mandelbrot;
                //break;
            case "terrain":
                return new Terrain(3);
            default:
                return new Triangle(someCrds, Colors.RED);
                //break;

        }

        //return wp;
    }






}
