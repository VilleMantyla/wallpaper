package com.lucid.wallpapercreator;

import android.graphics.Point;
import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.LinkedList;

/**
 * Created by Ville on 9.4.2017.
 */

public class Mandelbrot implements Wallpaper {
    int width = 360; //resolution - max should be the phone's resolution
    int height = 360; //resolution - max should be the phone's resolution
    LinkedList<Float> openGLPoints = new LinkedList<Float>();
    float openGLRatio = 1.0f/width;
    float[] glreadypoints;

    float[] backgroundColor = new float[4];

    private  float openGLPointSize;

    private final int glProgram;

    private static final int COORDS_PER_VERTEX = 2;
    private static final int BYTES_PER_FLOAT = 4;
    private final FloatBuffer vertexData;
    private int positionHandle;

    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    int maxIterations = 250;

    //public String[][] kuvio;

    double zoom = 2.5/width;

    float[] color;


    public Mandelbrot(Point screenSize) {
        //Define OpenGL point size according to phone's aspect ratio
        openGLPointSize = screenSize.x/(float)width;
        createVertexShader(openGLPointSize);
        color = ColorHelper.randomColor();

        //kuvio = new String[width][height];
        for(int pixelY = 0; pixelY < height; pixelY++) {
            for(int pixelX = 0; pixelX < width; pixelX++) {
                //TODO this fix centering
                double scaledX = (width/4.8-pixelX)*zoom; //selecting the center of the set
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
                if(iteration<maxIterations) {
                    //colorss
                }
                else {//belongs to mandelbrot set
                    openGLPoints.add((pixelX - width / 2) * openGLRatio);
                    openGLPoints.add((pixelY - width / 2) * openGLRatio);
                }
            }
        }

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

    private String vertexShaderCode;


    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    private void createVertexShader(float pointSize) {
        vertexShaderCode = "uniform mat4 uMVPMatrix;" +
                "attribute vec4 vPosition;" +
                "void main() {" +
                "  gl_Position = uMVPMatrix * vPosition;" +
                "  gl_PointSize = " + pointSize + ";" +
                "}";
    }

    @Override
    public void draw(float[] mvpMatrix) {

        GLES20.glClearColor(backgroundColor[0], backgroundColor[1], backgroundColor[2], 1.0f);

        GLES20.glUseProgram(glProgram);
        positionHandle = GLES20.glGetAttribLocation(glProgram, "vPosition");

        GLES20.glEnableVertexAttribArray(positionHandle);

        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexData);

        int colorHandle = GLES20.glGetUniformLocation(glProgram, "vColor");

        GLES20.glUniform4fv(colorHandle, 1, color, 0);

        // get handle to shape's transformation matrix
        int mMVPMatrixHandle = GLES20.glGetUniformLocation(glProgram, "uMVPMatrix");

        // Pass the projection and view transformation to the shader
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        //http://stackoverflow.com/questions/22296510/what-does-stride-mean-in-opengles
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, glreadypoints.length/COORDS_PER_VERTEX);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(positionHandle);
    }

    @Override
    public void changeColor(float[] c) {
        backgroundColor = ColorHelper.randomColor();
        color = c;
    }

}
