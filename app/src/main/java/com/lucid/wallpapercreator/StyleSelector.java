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

    private static PlainColor plainColor;

    public static Wallpaper getNewStyle(String style) {

        Wallpaper wp;

        //TODO create only once!
        switch (style) {
            case "background":
                return (plainColor == null) ? plainColor = new PlainColor() : plainColor;
                //break;
            case "lines":
                return new RandomLines(7);
            case "sierpinski":
                return new SierpinskiTriangle(6, new float[]{0,0}, 0.95f, ColorHelper.randomColor());
            case "colorful_sierpinski":
                return (sierpinskiTriangle == null) ? sierpinskiTriangle = new SierpinskiTriangle(6, new float[]{0,0}, 0.95f, ColorHelper.randomColor(), false) : sierpinskiTriangle;
                //break;
            case "mandelbrot":
                //mandelbrot = (mandelbrot == null) ? new Mandelbrot(screenSize) : mandelbrot;
                return (mandelbrot == null) ? mandelbrot = new Mandelbrot(screenSize) : mandelbrot;
                //break;
            case "terrain":
                return new Terrain(3);
            default:
                return new Triangle(someCrds, ColorHelper.RED);
                //break;

        }

        //return wp;
    }






}
