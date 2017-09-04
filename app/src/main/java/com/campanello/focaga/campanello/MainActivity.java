package com.campanello.focaga.campanello;

import android.app.Activity;
import android.app.usage.UsageEvents;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    Button campanello;
    MediaPlayer mediaPlayer = new MediaPlayer();
    MediaPlayer mediaPlayer2 = new MediaPlayer();
    Boolean bool = true;
    int count;
    long start = -1;
    long runTime = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        campanello = (Button) findViewById(R.id.campanello);
       // mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bell);
        count = 0;

        campanello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (start == -1) {
                    start = System.currentTimeMillis();
                }
                if (bool) {
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bell);
                    mediaPlayer.start();
                    bool = false;
                    mediaPlayer2.release();
                    count ++;
                }
                else {
                    mediaPlayer2 = MediaPlayer.create(getApplicationContext(), R.raw.bell);
                    mediaPlayer2.start();
                    bool = true;
                    mediaPlayer.release();
                    count ++;
                }
                runTime = System.currentTimeMillis() - start;
                if (runTime > 120000) {
                    if (count > 100) {
                        //popup
                    }
                    else {
                        start = -1;
                    }
                }

            }
        });


    }


    //blocca il tasto return
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
