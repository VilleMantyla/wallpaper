package com.lucid.wallpapercreator;

import android.opengl.GLES20;

import java.util.LinkedList;

/**
 * Created by Ville on 11.4.2017.
 */

public class RandomLines implements Wallpaper {

    private LinkedList<Line> lines;

    private int level;

    RandomLines(int n){
        level = n;
        lines = new LinkedList<>();
        while(level>0) {
            lines.add(createRandomHorizontalLine());
            level--;
        }
    }

    private static Line createRandomHorizontalLine() {
        float xStart = -1f; float yStart = (float)(Math.random()*2)-1f;
        float xEnd = 1f; float yEnd = (float)(Math.random()*2)-1f;

        return new Line(new float[]{xStart, yStart, xEnd, yEnd});
    }


    @Override
    public void draw(float[] mvpMatrix) {
        GLES20.glClearColor(0f,0f,0f,1f);
        for(Line line : lines) {
            line.draw(mvpMatrix);
        }
    }

    @Override
    public void changeColor(float[] c) {
        for(Line line : lines) {
            line.changeColor(ColorHelper.WHITE);
        }
    }
}
