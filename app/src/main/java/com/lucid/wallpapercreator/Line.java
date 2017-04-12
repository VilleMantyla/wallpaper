package com.lucid.wallpapercreator;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Defines the line shape and how to draw it.
 */

public class Line implements Wallpaper {

    private float[] lineCoordinates;

    private static final int COORDS_PER_VERTEX = 2;
    private FloatBuffer vertexBuffer;
    private final int glProgram;

    /*Initial color*/
    private float[] color;

    public Line(float[] line) {
        color = ColorHelper.WHITE;
        lineCoordinates = line;
        ByteBuffer bb = ByteBuffer.allocateDirect(
                lineCoordinates.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(lineCoordinates);
        vertexBuffer.position(0);

        int vertexShader = MyRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = MyRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);


        glProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(glProgram, vertexShader);
        GLES20.glAttachShader(glProgram, fragmentShader);
        GLES20.glLinkProgram(glProgram);
    }

    public void draw(float[] mvpMatrix) {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(glProgram);
        int positionHandle = GLES20.glGetAttribLocation(glProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(positionHandle);

        // Prepare the line coordinate data
        GLES20.glVertexAttribPointer(
                positionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT,
                false, COORDS_PER_VERTEX * 4, vertexBuffer);

        int colorHandle = GLES20.glGetUniformLocation(glProgram, "vColor");

        // Set the color
        GLES20.glUniform4fv(colorHandle, 1, color, 0);

        int mMVPMatrixHandle = GLES20.glGetUniformLocation(glProgram, "uMVPMatrix");

        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        GLES20.glLineWidth(3f);

        // Draw the line
        GLES20.glDrawArrays(GLES20.GL_LINES, 0, lineCoordinates.length / COORDS_PER_VERTEX);

        GLES20.glDisableVertexAttribArray(positionHandle);
    }

    @Override
    public void changeColor(float[] c) {
        color = c;
    }

    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

}
