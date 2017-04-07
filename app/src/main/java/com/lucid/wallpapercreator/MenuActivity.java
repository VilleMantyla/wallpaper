package com.lucid.wallpapercreator;

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

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.colors:
                if (checked)
                    // Random colors
                    break;
            case R.id.sierpinski:
                if (checked)
                    // Sierpinski
                    break;
        }

    }
}
