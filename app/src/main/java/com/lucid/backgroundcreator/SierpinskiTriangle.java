package com.lucid.backgroundcreator;

/**
 * Created by Ville on 3.4.2017.
 */

public class SierpinskiTriangle {

    private float[] centerPoint;

    private Triangle[] triangles;

    public static final float[] BLACK = {0f,0f,0f,1f};
    public static final float[] WHITE = {1f,1f,1f,1f};

    public static void createSierpinksiTri(int level) {

    }

    public static Triangle centerTriangle(Triangle tri) {
        float sideLength = tri.getSideLength()/2.0f;
        Triangle temp = new Triangle(tri.getCentroid(), sideLength, BLACK);
        return temp;
        //return new Triangle(sideLength, tri.getCentroid(), BLACK);
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
