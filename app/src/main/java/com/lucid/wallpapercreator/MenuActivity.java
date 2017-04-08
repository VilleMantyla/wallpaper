package com.lucid.wallpapercreator;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    private TextView backgrounds;
    private ViewGroup scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        String style = "no style";

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.triangle:
                if (checked)
                    // Random colors
                {style = "triangle";}
                    break;
            case R.id.sierpinski:
                if (checked) {
                    style = "sierpinski";
                }
                break;

        }
        Intent data = new Intent();
        data.setData(Uri.parse(style));
        setResult(RESULT_OK, data);
        finish();

    }
}
