package com.lucid.wallpapercreator;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //String[][] mandel = new Mandelbrot().kuvio;



    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        String style = "";

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.triangle:
                if (checked) {
                    style = "triangle";
                }
                    break;
            case R.id.sierpinski:
                if (checked) {
                    style = "sierpinski";
                }
                break;
            case R.id.mandelbrot:
                if(checked) {
                    style = "mandelbrot";
                }
                break;

        }

        Intent data = new Intent();
        data.setData(Uri.parse(style));
        setResult(RESULT_OK, data);
        finish();

    }
}
