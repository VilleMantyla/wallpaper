package com.lucid.wallpapercreator;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

public class WallpaperActivity extends AppCompatActivity {

    private GLSurfaceView GLView;
    private MyGLSurfaceView myGLSurfView;

    //private Point screenSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);

        //Intent intent = getIntent();

        //System.out.println(intent.getStringExtra("style"));

        myGLSurfView = (MyGLSurfaceView) findViewById(R.id.glSurface);

        GLView = myGLSurfView;
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String returnedResult = data.getData().toString();
                System.out.println(returnedResult);
                    myGLSurfView.getmRenderer().setWpstyle(returnedResult);
                    myGLSurfView.getmRenderer().styleChanged = true;
            }
        }
    }

    /** Changes the phone's background. Wallpaper will be created only
     * on this method.
     */
    public void changePhoneBackground(View v) throws IOException {

        myGLSurfView.createWallpaper();

        while(true) {
            if(myGLSurfView.getmRenderer().getCreatingWallpaper())
                continue;
            else
                break;
        }

        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        wallpaperManager.setBitmap(myGLSurfView.getmRenderer().getWallpaperBitmap());

        /* TOAST */
        Context context = getApplicationContext();
        CharSequence text = "Wallpaper changed!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void goToMenu(View view) {
        //Intent intent = new Intent(WallpaperActivity.this, MenuActivity.class);
        //startActivity(intent);


        startActivityForResult(new Intent(WallpaperActivity.this, MenuActivity.class), 1);
    }
}
