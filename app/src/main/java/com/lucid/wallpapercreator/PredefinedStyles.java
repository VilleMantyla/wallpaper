package com.lucid.wallpapercreator;

import android.graphics.Point;

/**
 * Provides a set of predefined styles for implemented wallpapers
 * in this program.
 */

public final class PredefinedStyles {

    public static Point screenSize;

    private static final float[] triangle = new float[]{
            -0.3f, -0.3f, 0.0f, // bottom left
            .0f,  0.6f, 0.0f, // top
            0.3f, -0.3f, 0.0f  // bottom right
    };

    private static PlainColor plainColor;

    private static RandomLines lines;

    private static SierpinskiTriangle solidSierpinski;

    private static SierpinskiTriangle colorfulSierpinski;

    private static Mandelbrot mandelbrot;

    /**
     * Returns a predefined wallpaper style according to a given style.
     */
    public static Wallpaper getNewStyle(String style) {

        switch (style) {
            case "background":
                return (plainColor == null) ? plainColor = new PlainColor() : plainColor;
            case "lines":
                return (lines == null) ? lines = new RandomLines(7) : lines;
            case "sierpinski":
                return (solidSierpinski == null) ?
                        solidSierpinski = new SierpinskiTriangle(
                                6, new float[]{0,0}, 0.95f, ColorHelper.randomColor())
                        : solidSierpinski;
            case "colorful_sierpinski":
                return (colorfulSierpinski == null) ?
                        colorfulSierpinski = new SierpinskiTriangle(
                                6, new float[]{0,0}, 0.95f, ColorHelper.randomColor(), false)
                        : colorfulSierpinski;
            case "mandelbrot":
                return (mandelbrot == null) ? mandelbrot = new Mandelbrot(screenSize) : mandelbrot;
            case "terrain":
                return new Terrain(3);
            default:
                return new Triangle(triangle, ColorHelper.RED);
        }
    }
}
