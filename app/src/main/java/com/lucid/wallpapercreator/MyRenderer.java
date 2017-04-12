package com.lucid.wallpapercreator;

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

    /* Wallpaper in bitmap format */
    private volatile Bitmap wallpaperBitmap;
    /* If this is true, wallpaperBitmap will be created
     on onDrawFrame method on current frame */
    private boolean creatingWallpaper = false;

    private volatile Point screenSize;

    private Wallpaper wallpaper;
    private String wallpaperStyle;
    /* Should wallpaper style be changed on this frame? */
    private boolean styleChanged = false;

    private float[] backgroundColor;


    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];


    public MyRenderer(Point screen) {
        screenSize = screen;
    }

    public Bitmap getWallpaperBitmap() {
        return wallpaperBitmap;
    }


    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Initial color value
        backgroundColor = ColorHelper.BLACK;
        // Set the initial background frame color
        GLES20.glClearColor(backgroundColor[0], backgroundColor[1], backgroundColor[2], 1.0f);
        wallpaper = new PlainColor();
    }

    public void onDrawFrame(GL10 unused) {
        // Clears the buffers
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        // Set the background color
        GLES20.glClearColor(backgroundColor[0], backgroundColor[1], backgroundColor[2], 1.0f);
        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        // Calculates the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        //Draw the wallpaper
        wallpaper.draw(mMVPMatrix);

        // Create a wallpaper on this frame?
        if(creatingWallpaper) {
            wallpaperBitmap = createBitmapFromGLSurface(0, 0, screenSize.x, screenSize.y);
            creatingWallpaper = false;
        }

        // Change the wallpaper style on this frame?
        if(styleChanged) {
            styleChanged = false;
            wallpaper = PredefinedStyles.getNewStyle(wallpaperStyle);
        }
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);

    }

    public static int loadShader(int type, String shaderCode){

        int shader = GLES20.glCreateShader(type);

        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    /**
     * Creates a bitmap from the GLSurface.
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

    /* Create a wallpaper from this frame */
    public void createWallpaper() {
        creatingWallpaper = true;
    }
    /* Check if the wallpaper is still under construction  */
    public boolean getCreatingWallpaperLock() {
        return creatingWallpaper;
    }
    /* Set new wallpaper style */
    public void setWallpaperStyle(String newStyle) {
        wallpaperStyle = newStyle;
        styleChanged = true;
    }

    public void setBackgroundColor(float[] newColor) {
        backgroundColor = newColor;
    }

    public void changeColors(float[] color) {
        wallpaper.changeColor(color);
    }
}

