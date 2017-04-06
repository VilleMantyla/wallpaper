package com.lucid.backgroundcreator;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;

public class WallpaperActivity extends AppCompatActivity {

    private GLSurfaceView GLView;
    private MyGLSurfaceView myGLSurfView;

    private Point screenSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);

        //screenSize = new Point();
        //getWindowManager().getDefaultDisplay().getRealSize(screenSize);

        myGLSurfView = (MyGLSurfaceView) findViewById(R.id.glSurface);//new MyGLSurfaceView(this, screenSize);

        //myGLSurfView.setScreenSize(screenSize);

        //myGLSurfView = new MyGLSurfaceView(this, screenSize);
        GLView = myGLSurfView;


        //FrameLayout frameLayout = (FrameLayout)findViewById(R.id.myFrameLayout);

        //frameLayout.addView(GLView, 0);

    }

    //Full screen, immersive
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            GLView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }

    /** Changes the phone's background.
     */
    public void changePhoneBackground(View v) throws IOException {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        wallpaperManager.setBitmap(myGLSurfView.getmRenderer().getWallpaper());

        Context context = getApplicationContext();
        CharSequence text = "Wallpaper changed!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void goToMenu(View view) {
        Intent intent = new Intent(WallpaperActivity.this, MenuActivity.class);
        startActivity(intent);
    }
}
