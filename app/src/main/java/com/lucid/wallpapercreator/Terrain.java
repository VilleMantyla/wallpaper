package com.lucid.wallpapercreator;

import android.opengl.GLES20;

import java.util.LinkedList;

/**
 * Created by Ville on 10.4.2017.
 * 2D midpoint displacement
 */

public class Terrain implements Wallpaper{

    private LinkedList<Float> lineCoordsList;

    private Line line;

    private int level;
    private float[] backgroundColor;


    public Terrain(int lvl) {
        level = lvl;
        //int numberofFloatsRequired = ((int)Math.pow(level, 2)+1)*4;

        generateTerrain();
    }

    private void generateTerrain() {
        backgroundColor = new float[4];
        lineCoordsList = new LinkedList<>();
        float[] leftStart = {-1f,0f};
        lineCoordsList.add(leftStart[0]); lineCoordsList.add(leftStart[1]);
        float[] rightStart = {1f,0f};
        midPointDisplacement(leftStart, rightStart, level);
        lineCoordsList.add(rightStart[0]); lineCoordsList.add(rightStart[1]);

        createLineFromList();

    }

    private void createLineFromList() {
        //if(lineee == null) {
        //int numberofFloatsRequired = ((int)Math.pow(level, 2)+1)*4;
        float[] lineCoordinates = new float[lineCoordsList.size()*2-2]; //TODO this just works but how???
        System.out.println("listan pituus: " + lineCoordinates.length);

        //when level is 2
        lineCoordinates[0] = lineCoordsList.get(0);
        lineCoordinates[1] = lineCoordsList.get(1);
        lineCoordinates[2] = lineCoordsList.get(2);
        lineCoordinates[3] = lineCoordsList.get(3);
        /*lineCoordinates[4] = points.get(2);
        lineCoordinates[5] = points.get(3);
        lineCoordinates[6] = points.get(4);
        lineCoordinates[7] = points.get(5);
        lineCoordinates[8] = points.get(4);
        lineCoordinates[9] = points.get(5);
        lineCoordinates[10] = points.get(6);
        lineCoordinates[11] = points.get(7);
        lineCoordinates[12] = points.get(6);
        lineCoordinates[13] = points.get(7);
        lineCoordinates[14] = points.get(8);
        lineCoordinates[15] = points.get(9);
        */

        int iter = 0;
        for (int i = 4; i + 4 < lineCoordinates.length; i += 4) {
            lineCoordinates[i] = lineCoordsList.get(i - 2 - iter * 2);
            lineCoordinates[i + 1] = lineCoordsList.get(i - 1 - iter * 2);
            lineCoordinates[i + 2] = lineCoordsList.get(i - iter * 2);
            lineCoordinates[i + 3] = lineCoordsList.get(i + 1 - iter * 2);
            iter++;
        }
        line = new Line(lineCoordinates);
    }

    private void midPointDisplacement(float[] left, float[] right, int level) {
        if(level <= 0) {
            return;
        }
        float[] mid = midpoint(left, right);
        mid[1] = mid[1]+(float)Math.random()*0.1f; //TODO FIX THIS
        midPointDisplacement(left, mid, level-1);
        lineCoordsList.add(mid[0]); lineCoordsList.add(mid[1]);
        midPointDisplacement(mid, right, level-1);
    }

    private static float[] midpoint(float[] left, float[] right) {
        float[] result = new float[2];
        result[0] = (right[0] + left[0]) / 2f;
        result[1] = (right[1] + left[1]) / 2f;
        return result;
    }

    @Override
    public void draw(float[] mvpMatrix) {
        //createLineFromList();
        GLES20.glClearColor(backgroundColor[0], backgroundColor[1], backgroundColor[2], 1.0f);
        line.draw(mvpMatrix);
    }

    @Override
    public void changeColor(float[] c) {
        //generateTerrain();
        line.changeColor(ColorHelper.randomColor());
    }

}
