package com.lucid.wallpapercreator;

import android.graphics.Color;
import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.LinkedList;

/**
 * Created by Ville on 9.4.2017.
 */

public class Mandelbrot implements Wallpaper {
    int width = 500; //sample amount
    int height = 500;
    LinkedList<Float> openGLPoints = new LinkedList<Float>();
    float openGLRatio = 1.0f/width;
    float[] glreadypoints;
    private final int glProgram;

    private static final int COORDS_PER_VERTEX = 2;
    private static final int BYTES_PER_FLOAT = 4;
    private final FloatBuffer vertexData;// = ByteBuffer.allocateDirect(glreadypoints.length *BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
    private int positionHandle;

    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex


    float[] color1 = Colors.BLACK;
    float[] color2 = Colors.RED;

    int maxIterations = 200;

    //public String[][] kuvio;

    double zoom = 2.5/width;


    public Mandelbrot() {

        //kuvio = new String[width][height];
        for(int pixelY = 0; pixelY < height; pixelY++) {
            for(int pixelX = 0; pixelX < width; pixelX++) {
                //TODO this fix centering
                double scaledX = (width/4.3-pixelX)*zoom; //selecting the center of the set
                double scaledY = (pixelY-height/2)*zoom;
                double x = 0;
                double y = 0;
                int iteration = 0;
                while (x*x + y*y < 2*2 && iteration < maxIterations) {
                    double xtemp = x*x-y*y+scaledX;
                    y = 2*x*y + scaledY;
                    x = xtemp;
                    iteration++;
                }
                if(iteration<maxIterations)
                {
                    //kuvio[pixelY][pixelX] = " ";
                }
                else {//belongs to mandelbrot set
                    //kuvio[pixelY][pixelX] = "*";
                    openGLPoints.add((pixelX - width / 2) * openGLRatio);
                    openGLPoints.add((pixelY - width / 2) * openGLRatio);
                }
            }
        }
        /*for(int i=0; i< width; i++){
            System.out.println("");
            for (int j=0; j<height; j++)
            {
                System.out.print(kuvio[i][j]);
            }
        }*/

        glreadypoints = listToFloatArray(openGLPoints);

        vertexData = ByteBuffer.allocateDirect(glreadypoints.length *BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexData.put(glreadypoints);
        vertexData.position(0);

        int vertexShader = MyRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = MyRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        glProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(glProgram, vertexShader);
        GLES20.glAttachShader(glProgram, fragmentShader);
        GLES20.glLinkProgram(glProgram);
    }

    private float[] listToFloatArray(LinkedList<Float> openGLPoints) {
        float[] result = new float[openGLPoints.size()];
        for(int i=0; i<result.length;i++) {
            result[i] = openGLPoints.get(i);
        }
        return result;
    }

    private final String vertexShaderCode =
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "  gl_PointSize = 5.0;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";
    @Override
    public void draw(float[] mvpMatrix) {
        GLES20.glUseProgram(glProgram);
        positionHandle = GLES20.glGetAttribLocation(glProgram, "vPosition");
        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(positionHandle);

        int colorHandle = GLES20.glGetUniformLocation(glProgram, "vColor");

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexData);

        // get handle to fragment shader's vColor member
        colorHandle = GLES20.glGetUniformLocation(glProgram, "vColor");

        GLES20.glUniform4fv(colorHandle, 1, Colors.WHITE, 0);

        //http://stackoverflow.com/questions/22296510/what-does-stride-mean-in-opengles
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, glreadypoints.length/COORDS_PER_VERTEX);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(positionHandle);

        // get handle to shape's transformation matrix
        int mMVPMatrixHandle = GLES20.glGetUniformLocation(glProgram, "uMVPMatrix");

        // Pass the projection and view transformation to the shader
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, glreadypoints.length/COORDS_PER_VERTEX);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(positionHandle);
    }

}
