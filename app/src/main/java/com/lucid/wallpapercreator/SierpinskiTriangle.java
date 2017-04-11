package com.lucid.wallpapercreator;

import android.opengl.GLES20;

/**
 * Implements Sierpinski triangle.
 */

public class SierpinskiTriangle implements Wallpaper {

    /*All the "smallest" triangles in Sierpinski*/
    private Triangle[] triangles;
    /*Only used to help with indexing the triangles*/
    private int triangleIndex = 0;
    /*Background color of the frame this shape is drawn on*/
    private float[] backgroundColor = new float[4];

    /*Initial color of the triangles*/
    private static float[] color;

    /* Should the Sierpinski triangle be monochrome */
    private boolean monochrome;



    public SierpinskiTriangle(int level, float[] centroid, float sideLength, float[] clr) {
        monochrome = true;
        triangles = new Triangle[(int)Math.pow(3, level-1)];
        color = clr;
        float[] eTria = equilateralTriangle(sideLength, centroid);
        float[] top = {eTria[0], eTria[1]};
        float[] left = {eTria[2], eTria[3]};
        float[] right = {eTria[4], eTria[5]};
        sierpinskiDivide(level, top, left, right);
    }

    public SierpinskiTriangle(int level, float[] centroid, float sideLength, float[] clr, boolean solid) {
        monochrome = solid;
        triangles = new Triangle[(int)Math.pow(3, level-1)];
        color = clr;
        float[] eTria = equilateralTriangle(sideLength, centroid);
        float[] top = {eTria[0], eTria[1]};
        float[] left = {eTria[2], eTria[3]};
        float[] right = {eTria[4], eTria[5]};
        sierpinskiDivide(level, top, left, right);
    }

    private void sierpinskiDivide(int level, float[] top, float[] left, float[] right) {
        if(level == 1) {
            triangles[triangleIndex++] = new Triangle(new float[]{
                    top[0], top[1], 0f,
                    left[0], left[1], 0f,
                    right[0], right[1], 0f}, color);
        }
        else {
            float[] midleft = midpoint(left, top);
            float[] midright = midpoint(top, right);
            float[] midcenter = midpoint(left, right);

            sierpinskiDivide(level-1, top, midleft, midright);
            sierpinskiDivide(level-1, midleft, left, midcenter);
            sierpinskiDivide(level-1, midright, midcenter, right);
        }
    }

    private static float[] midpoint(float[] point1, float[] point2) {
        return new float[]{(point1[0] + point2[0])/2f, (point1[1] + point2[1])/2f};
    }

    private static float[] equilateralTriangle(float sideLength, float[] centroid) {
        float height = (float)(sideLength*((Math.sqrt(3.0))/2.0));
        float[] peak = {centroid[0], centroid[1]+(height*2)/3};
        float[] left = {centroid[0]-sideLength/2, centroid[1]-height/3};
        float[] right = {centroid[0]+sideLength/2, centroid[1]-height/3};

        return new float[]{
                peak[0], peak[1],
                left[0], left[1],
                right[0], right[1],
        };
    }

    /**
     * Draws the monochrome Sierpinski on a black background. Non monochrome
     * Sierpinski is drawn on a background randomly colored.
     */
    @Override
    public void draw(float[] mvpMatrix) {
        if(monochrome)
            GLES20.glClearColor(backgroundColor[0], backgroundColor[1], backgroundColor[2], 1.0f);
        else
            GLES20.glClearColor(0, 0, 0, 1.0f); //black
        for (Triangle tri : triangles) {
            tri.draw(mvpMatrix);
        }
    }

    /**
     * If the monochrome boolean is true, the Sierpinski triangle will be
     * monochrome and have the color value of c. If monochrome  false, all the
     * triangles will be colored randomly. Also a new background color is
     * randomized for the monochrome Sierpinski.
     */
    @Override
    public void changeColor(float[] c) {
        if(monochrome) {
            backgroundColor = ColorHelper.randomColor();
            for (Triangle tri : triangles) {
                tri.changeColor(c);
            }
        }else {
            for (Triangle tri : triangles) {
                tri.changeColor(ColorHelper.randomColor());
            }
        }

    }

}
