package com.lucid.wallpapercreator;

/**
 * Created by Ville on 8.4.2017.
 */

public final class StyleSelector {

    private static float[] someCrds = new float[]{
            -0.5f, -0.311004243f, 0.0f, // bottom left
            .0f,  0.622008459f, 0.0f, // top
            0.5f, -0.311004243f, 0.0f  // bottom right
    };

    public static Wallpaper getNewStyle(String style) {

        Wallpaper wp;

        switch (style) {
            case "triangle":
                wp = new Triangle(someCrds, Colors.WHITE);
                break;
            case "sierpinski":
                wp = new SierpinskiTriangle(6, new float[]{0,0}, 0.95f, Colors.BLUE);
                break;
            case "mandelbrot":
                wp = new Mandelbrot();
                break;
            default:
                wp = new Triangle(someCrds, Colors.RED);
                break;

        }

        return wp;
    }






}
