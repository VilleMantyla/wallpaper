package com.lucid.backgroundcreator;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Ville on 30.3.2017.
 */

public class Triangle {

    private float[] coordinates;
    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;

    private final int vertexCount;

    private float sideLength;

    private float[] centroid;

    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    private FloatBuffer vertexBuffer;

    private int positionHandle;

    private int colorHandle;

    // Use to access and set the view transformation
    private int mMVPMatrixHandle;

    private final int glProgram;

    // this is just an example
    static float exampleTriangle[] = {   // in counterclockwise order:
            0.0f,  0.622008459f, 0.0f, // top
            -0.5f, -0.311004243f, 0.0f, // bottom left
            0.5f, -0.311004243f, 0.0f  // bottom right
    };

    // Set color with red, green, blue and alpha (opacity) values
    public float[] color;

    public Triangle(float[] coords) {
        /* TODO centroid?? */
        coordinates = coords;
        sideLength = calculateSideLength();
        vertexCount = coords.length / COORDS_PER_VERTEX;
        glProgram = GLES20.glCreateProgram();
        initialize(coords);
    }

    /* Creates equilateral triangle */
    public Triangle(float sdLength, float[] cntroid, float[] clr) {
        sideLength = sdLength;
        centroid = cntroid;
        color = clr;
        vertexCount = 9 / COORDS_PER_VERTEX;
        glProgram = GLES20.glCreateProgram();
        initialize(calculateCoordinates(sideLength, centroid));
    }

    /* Upside down equilateral triangle */
    public Triangle(float[] cntroid, float sdLength, float[] clr) {
        sideLength = sdLength;
        centroid = cntroid;
        color = clr;
        vertexCount = 9 / COORDS_PER_VERTEX;
        glProgram = GLES20.glCreateProgram();
        initialize(calculateCoordinates(centroid, sideLength));
    }

    public float[] getCentroid() {
        float[] temp = new float[centroid.length];
        for (int i=0; i<temp.length; i++) {
            temp[i] = centroid[i];
        }
        return temp;
    }

    public float getSideLength() {
        return  sideLength;
    }

    private float calculateSideLength() {
        return (float)Math.sqrt((Math.pow((coordinates[0] - coordinates[2]), 2)
                + Math.pow((coordinates[1] - coordinates[3]), 2)));
    }

    private float[] calculateCoordinates(float sideLength, float[] centroid) {
        float height = (float)(sideLength*((Math.sqrt(3.0))/2.0));
        float[] peak = {centroid[0], centroid[1]+(height*2)/3};
        float[] left = {centroid[0]-sideLength/2, centroid[1]-height/3};
        float[] right = {centroid[0]+sideLength/2, centroid[1]-height/3};

        return coordinates = new float[]{
                peak[0], peak[1], 0f,
                left[0], left[1], 0f,
                right[0], right[1], 0f
        };
    }

    private float[] calculateCoordinates(float[] centroid, float sideLength) {
        float height = (float)(sideLength*((Math.sqrt(3.0))/2.0));
        float[] peak = {centroid[0], centroid[1]-(height*2)/3};
        float[] left = {centroid[0]-sideLength/2, centroid[1]+height/3};
        float[] right = {centroid[0]+sideLength/2, centroid[1]+height/3};

        return coordinates = new float[]{
                peak[0], peak[1], 0f,
                left[0], left[1], 0f,
                right[0], right[1], 0f
        };
    }

    private void initialize(float[] coordinates) {
        //vertexCount = coordinates.length / COORDS_PER_VERTEX;
        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(coordinates.length * 4);
        // use the device hardware's native byte order
        bb.order(ByteOrder.nativeOrder());

        // create a floating point buffer from the ByteBuffer
        vertexBuffer = bb.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        vertexBuffer.put(coordinates);
        // set the buffer to read the first coordinate
        vertexBuffer.position(0);

        int vertexShader = MyRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = MyRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        // create empty OpenGL ES Program
        //glProgram = GLES20.glCreateProgram();

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
        positionHandle = GLES20.glGetAttribLocation(glProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(positionHandle);

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // get handle to fragment shader's vColor member
        colorHandle = GLES20.glGetUniformLocation(glProgram, "vColor");

        // Set color for drawing the triangle
        GLES20.glUniform4fv(colorHandle, 1, color, 0);

        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(positionHandle);

        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES20.glGetUniformLocation(glProgram, "uMVPMatrix");

        // Pass the projection and view transformation to the shader
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(positionHandle);

    }


    private final String vertexShaderCode =
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";


}
