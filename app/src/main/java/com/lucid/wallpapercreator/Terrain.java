package com.lucid.wallpapercreator;

import java.util.LinkedList;

/**
 * 2D midpoint displacement used to create a terrain.
 */

public class Terrain implements Wallpaper{
    /*Coordinates of the line that represent the terrain*/
    private LinkedList<Float> lineCoordsList;
    /*The terrain*/
    private Line line;
    /*Complexity of the terrain*/
    private int level;


    public Terrain(int lvl) {
        level = lvl;
        generateTerrain();
    }

    private void generateTerrain() {
        lineCoordsList = new LinkedList<>();
        float[] leftStart = {-1f,0f};
        lineCoordsList.add(leftStart[0]); lineCoordsList.add(leftStart[1]);
        float[] rightStart = {1f,0f};
        midPointDisplacement(leftStart, rightStart, level);
        lineCoordsList.add(rightStart[0]); lineCoordsList.add(rightStart[1]);

        createContinousLine();
    }

    private void createContinousLine() {
        float[] lineCoordinates = new float[lineCoordsList.size()*2-2]; //TODO ???

        int iteration = 0;
        for (int i = 0; i + 4 < lineCoordinates.length; i += 4) {
            lineCoordinates[i] = lineCoordsList.get(i - iteration * 2);
            lineCoordinates[i + 1] = lineCoordsList.get(i + 1 - iteration * 2);
            lineCoordinates[i + 2] = lineCoordsList.get(i + 2 - iteration * 2);
            lineCoordinates[i + 3] = lineCoordsList.get(i + 3 - iteration * 2);
            iteration++;
        }
        line = new Line(lineCoordinates);
    }

    private void midPointDisplacement(float[] left, float[] right, int level) {
        if(level <= 0) {
            return;
        }
        float[] mid = midpoint(left, right);
        mid[1] = mid[1]+(float)Math.random()*0.1f; //TODO Make this... nicer
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
        line.draw(mvpMatrix);
    }

    @Override
    public void changeColor(float[] c) {
        line.changeColor(c);
    }

}
