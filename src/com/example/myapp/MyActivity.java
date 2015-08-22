package com.example.myapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import com.example.myapp.view.SlideMenu;

public class MyActivity extends Activity {
    SlideMenu sm;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        sm = (SlideMenu) findViewById(R.id.sm);
    }


    public void toggle(View v) {

        sm.toggle();


    }
}
