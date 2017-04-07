package com.lucid.wallpapercreator;

/**
 * Created by Ville on 3.4.2017.
 */

public class SierpinskiTriangle implements WallpaperStyle {

    private float[] centerPoint;

    private static Triangle[] triangles;

    public static final float[] BLACK = {0f,0f,0f,1f};
    public static final float[] WHITE = {1f,1f,1f,1f};

    private static int triangleIndex = 0;
    private static float[] color;

    public SierpinskiTriangle() {
    }

    public static Triangle[] createSierpinskiTri(int level, float[] centroid, float sideLength, float[] clr) {
        triangles = new Triangle[(int)Math.pow(3, level-1)];
        color = clr;
        //triangleIndex = triangles.length;
        float[] eTria = equilateralTriangle(sideLength, centroid);
        float[] top = {eTria[0], eTria[1]};
        float[] left = {eTria[2], eTria[3]};
        float[] right = {eTria[4], eTria[5]};
        sierpinskiDivide(level, top, left, right);
        return triangles;
    }

    public static void sierpinskiDivide(int level, float[] top, float[] left, float[] right) {

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

    @Override
    public void piirra(float[] mvpMatrix) {
        //Triangle[] tyhmaaa = new SierpinskiTriangle().createSierpinskiTri(6, new float[]{0,0}, 0.95f, Triangle.WHITE);
        for (Triangle tri : triangles) {
            tri.draw(mvpMatrix);
        }
    }

    @Override
    public void createWPStyle() {

        createSierpinskiTri(6, new float[]{0,0}, 0.95f, Triangle.WHITE);
        //return null;
    }

    /*public void sierpinskiTriangle(EquilateralTriangle triangle, int bgColor, int trColor) {
        drawTriangle(triangle, trColor);

        if (triangle.getSideLength() < 200.0) {
            return;
        } else {
            EquilateralTriangle[] triangles;
            triangles = divideTriangle(triangle);
            sierpinskiTriangle(triangles[1], bgColor, trColor);
            sierpinskiTriangle(triangles[2], bgColor, trColor);
            sierpinskiTriangle(triangles[3], bgColor, trColor);
            destroyTriangle(triangles[0], bgColor);
        }
    }*/

}
