package com.lucid.backgroundcreator;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.egl.EGLConfig;


/**
 * Created by Ville on 22.3.2017.
 */

public class MyRenderer implements GLSurfaceView.Renderer {

    private volatile float[] randomColor;

    private volatile Bitmap backgroundBitmap;

    private volatile int screenWidth = 100;
    private volatile int screenHeight = 100;

    public Bitmap getBackgroundBitmap()
    {
        return backgroundBitmap;
    }

    public void setRandomColor(float[] c)
    {
        randomColor = c;
    }

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        randomColor = new float[3];
    }

    public void onDrawFrame(GL10 unused) {
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(randomColor[0], randomColor[1], randomColor[2], 1.0f);
        backgroundBitmap = createBitmapFromGLSurface(0, 0, screenWidth, screenHeight);
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }


    public static Bitmap createBitmapFromGLSurface(int xStart, int yStart, int w, int h){
        int openGLBitmapBuffer[] = new int[w * h];
        int bitmapSource[] = new int[w * h];
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

