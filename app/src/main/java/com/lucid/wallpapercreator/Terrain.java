package com.lucid.wallpapercreator;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.LinkedList;

/**
 * Created by Ville on 10.4.2017.
 * 2D midpoint displacement
 */

public class Terrain implements Wallpaper{

    private LinkedList<Float> points;



    public Terrain() {
        points = new LinkedList<>();
        int level = 5;
        float[] left = {-1f,0f};
        points.add(left[0]); points.add(left[1]);
        float[] right = {1f,0f};
        doTheTerrainBiatch(left, right, level);
        points.add(right[0]); points.add(right[1]);
    }

    private void doTheTerrainBiatch(float[] left, float[] right, int level) {
        if(level <= 0) {
            //points.
            return;
        }
        float[] mid = midpoint(left, right);
        mid[1] = mid[1]+(float)Math.random()*0.08f; //TODO FIX THIS
        doTheTerrainBiatch(left, mid, level-1);
        points.add(mid[0]); points.add(mid[1]);
        doTheTerrainBiatch(mid, right, level-1);
    }

    private static float[] midpoint(float[] left, float[] right) {
        float[] result = new float[2];
        result[0] = (right[0] + left[0]) / 2f;
        result[1] = (right[1] + left[1]) / 2f;
        return result;
    }

    private String fragmentShaderCode;

    private String vertexShaderCode;

    @Override
    public void draw(float[] mvpMatrix) {
        float[] asdf = new float[points.size()*2];

        //when level is 2
        asdf[0] = points.get(0);
        asdf[1] = points.get(1);
        asdf[2] = points.get(2);
        asdf[3] = points.get(3);
        /*asdf[4] = points.get(2);
        asdf[5] = points.get(3);
        asdf[6] = points.get(4);
        asdf[7] = points.get(5);
        asdf[8] = points.get(4);
        asdf[9] = points.get(5);
        asdf[10] = points.get(6);
        asdf[11] = points.get(7);
        asdf[12] = points.get(6);
        asdf[13] = points.get(7);
        asdf[14] = points.get(8);
        asdf[15] = points.get(9);
        */

        int iter = 0;
        for(int i=4;i+4<asdf.length;i+=4) {
            asdf[i] = points.get(i-2-iter*2);
            asdf[i+1] = points.get(i-1-iter*2);
            asdf[i+2] = points.get(i-iter*2);
            asdf[i+3] = points.get(i+1-iter*2);
            iter++;
        }
        new Line(asdf).draw(mvpMatrix);

    }

    @Override
    public void changeToRandomColor() {
        //

    }

}
