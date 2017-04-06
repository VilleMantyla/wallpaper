package com.lucid.backgroundcreator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    private TextView backgrounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        backgrounds = (TextView) findViewById(R.id.backgrounds);

        String[] backgroundNames = {"bg 1", "bg 2", "bg 3", "bg 4", "bg 5", "bg 6",
                "bg 7", "bg 8", "bg 9", "bg 10"};

        for(String backgroundName : backgroundNames) {
            backgrounds.append(backgroundName + "\n\n\n");
        }
    }
}
