package com.lucid.backgroundcreator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    private TextView backgrounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        backgrounds = (TextView) findViewById(R.id.backgrounds);

        String[] backgroundNames = {"bg 1", "bg 2", "bg 3"};

        for(String backgroundName : backgroundNames) {
            backgrounds.append(backgroundName);
        }
    }
}
