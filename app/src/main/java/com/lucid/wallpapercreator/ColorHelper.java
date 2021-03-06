package com.lucid.wallpapercreator;

/**
 * Created by Ville on 7.4.2017.
 */

public class ColorHelper {

    public static final float[] WHITE = {1f,1f,1f,1f};
    public static final float[] BLACK = {0f,0f,0f,1f};
    public static final float[] BLUE = {0f,0.0f,1f,1f};
    public static final float[] RED = {1f,0.0f,0f,1f};


    public static float[] randomColor() {
        float red = (float)Math.random();
        float green = (float)Math.random();
        float blue = (float)Math.random();
        return new float[]{red, green, blue, 1f};
    }

    public static float[] rgbToOpenGLrgba(int[] rgb) {
        float[] glrgba = new float[4];
        glrgba[0] = rgb[0]/255.0f;
        glrgba[1] = rgb[1]/255.0f;
        glrgba[2] = rgb[2]/255.0f;
        glrgba[3] = 1.0f;
        return glrgba;
    }
}
