package com.lucid.backgroundcreator;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.egl.EGLConfig;


/**
 * Created by Ville on 22.3.2017.
 */

public class MyRenderer implements GLSurfaceView.Renderer {

    private volatile float[] randomColor;

    /**/
    private volatile Bitmap wallpaper;

    private volatile Point screenSize;

    private Triangle triangle;

    private Triangle triangle2;

    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];


    public MyRenderer(Point screen) {
        screenSize = screen;

    }

    public Bitmap getWallpaper() {
        return wallpaper;
    }


    public void setRandomColor(float[] c)
    {
        randomColor = c;
    }

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        randomColor = new float[3];
        triangle = new Triangle(0.95f, new float[]{0,0,0}, new float[]{0.9f,0.29f,0.5f,1.0f});
        triangle2 = SierpinskiTriangle.centerTriangle(triangle);
        //triangle2 = new Triangle(0.5f, new float[]{0.2f,0.5f,0.5f,0}, new float[]{0.2f,0.9f,0.5f,1.0f});
    }

    public void onDrawFrame(GL10 unused) {
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(randomColor[0], randomColor[1], randomColor[2], 1.0f);
        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        // Draw shapes
        triangle.draw(mMVPMatrix);
        triangle2.draw(mMVPMatrix);

        /* Create wallpaper */
        wallpaper = createBitmapFromGLSurface(0, 0, screenSize.x, screenSize.y);
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);

    }

    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    /**
     * Creates Bitmap from GLSurface.
     * @param xStart
     * @param yStart
     * @param w
     * @param h
     * @return
     */
    public static Bitmap createBitmapFromGLSurface(int xStart, int yStart, int w, int h){
        int[] openGLBitmapBuffer = new int[w * h];
        int[] bitmapSource = new int[w * h];
        IntBuffer intBuffer = IntBuffer.wrap(openGLBitmapBuffer);
        intBuffer.position(0);
        GLES20.glReadPixels(xStart, yStart, w, h, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, intBuffer);

        //OpenGL bitmap to Android bitmap
        int offset;
        int offset2;
        for(int i=0, k=0; i<h; i++, k++)
        {
            offset = i * w;
            offset2 = (h - i - 1) * w;
            for(int j = 0; j < w; j++)
            {
                int glPixel = openGLBitmapBuffer[offset+j];
                int blue = (glPixel>>16)&0xff;
                int red = (glPixel<<16)&0x00ff0000;
                int pixel = (glPixel&0xff00ff00) | red | blue;
                bitmapSource[offset2+j]=pixel;
            }
        }

        return Bitmap.createBitmap(bitmapSource, w, h, Bitmap.Config.ARGB_8888);
    }
}

