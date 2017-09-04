package com.campanello.focaga.campanello;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class LauncherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        Intent mainActivity = new Intent(LauncherActivity.this, MainActivity.class);
        startActivity(mainActivity);
    }
}
