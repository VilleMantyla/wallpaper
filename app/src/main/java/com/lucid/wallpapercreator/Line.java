package com.lucid.wallpapercreator;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Ville on 10.4.2017.
 */

public class Line implements Wallpaper {

    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;

    private final int glProgram;

    float[] lineCoordinates;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 2;
    static float exampleLineCoords[] = {
            0.5f,  0.5f,   // top
            -0.5f, 0.5f,  // bottom
            0.5f,  0.4f,   // left
            -0.5f,  0.4f }; // right

    //private short drawOrder[] = { 0,1,2,0}; // order to draw vertices

    public Line(float[] line) {
        lineCoordinates = line;
        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 4 bytes per float)
                lineCoordinates.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(lineCoordinates);
        vertexBuffer.position(0);

        // initialize byte buffer for the draw list
        //ByteBuffer dlb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 2 bytes per short)
        //        drawOrder.length * 2);
        //dlb.order(ByteOrder.nativeOrder());
        //drawListBuffer = dlb.asShortBuffer();
        //drawListBuffer.put(drawOrder);
        //drawListBuffer.position(0);



        int vertexShader = MyRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = MyRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);


        // create empty OpenGL ES Program
        glProgram = GLES20.glCreateProgram();

        // add the vertex shader to program
        GLES20.glAttachShader(glProgram, vertexShader);

        // add the fragment shader to program
        GLES20.glAttachShader(glProgram, fragmentShader);

        // creates OpenGL ES program executables
        GLES20.glLinkProgram(glProgram);
    }

    public void draw(float[] mvpMatrix) {




        // Add program to OpenGL ES environment
        GLES20.glUseProgram(glProgram);

        // get handle to vertex shader's vPosition member
        int positionHandle = GLES20.glGetAttribLocation(glProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(positionHandle);

        // Prepare the line coordinate data
        //GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
        //        GLES20.GL_FLOAT, false,
        //        vertexStride, vertexBuffer);
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, COORDS_PER_VERTEX * 4, vertexBuffer);

        // get handle to fragment shader's vColor member
        int colorHandle = GLES20.glGetUniformLocation(glProgram, "vColor");

        // Set color for drawing the triangle
        GLES20.glUniform4fv(colorHandle, 1, Colors.RED, 0);

        // get handle to shape's transformation matrix
        //int mMVPMatrixHandle = GLES20.glGetUniformLocation(glProgram, "uMVPMatrix");

        // Pass the projection and view transformation to the shader
        //GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        // Draw the line
        //GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        //GLES20.glDrawElements(GLES20.GL_LINES, drawOrder.length, GLES20.GL_UNSIGNED_BYTE, drawListBuffer);
        GLES20.glDrawArrays(GLES20.GL_LINES, 0, lineCoordinates.length / COORDS_PER_VERTEX);


        // Disable vertex array
        GLES20.glDisableVertexAttribArray(positionHandle);



        //GLES20.glVer(3, GL_FLOAT, 0, vertices);


        //glVertexPointer(3, GL_FLOAT, 0, vertices);
        //glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_BYTE, indices);
    }

    @Override
    public void changeToRandomColor() {
        //
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
