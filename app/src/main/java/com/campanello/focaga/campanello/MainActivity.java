package com.campanello.focaga.campanello;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.usage.UsageEvents;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainActivity extends Activity {
    Button campanello;
    MediaPlayer mediaPlayer = new MediaPlayer();
    MediaPlayer mediaPlayer2 = new MediaPlayer();
    Boolean bool = true;
    Switch cambio;
    Object suono;
    int count;
    long start = -1;
    long runTime = -1;
    public static File fileSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        campanello = (Button) findViewById(R.id.campanello);
        cambio = (Switch) findViewById(R.id.cambio);
       // mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bell);
        count = 0;
        fileSave = new File(this.getFilesDir(), "vittoria.txt");
        if (fileSave.exists()){
            cambio.setVisibility(View.VISIBLE);
        }
        campanello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cambio.isChecked())
                    suono = R.raw.cameriere;
                else
                    suono = R.raw.bell;
                if (start == -1 && !fileSave.exists()) {
                    start = System.currentTimeMillis();
                }
                if (bool) {
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), (int)suono);
                    mediaPlayer.start();
                    bool = false;
                    mediaPlayer2.release();
                    count ++;
                }
                else {
                    mediaPlayer2 = MediaPlayer.create(getApplicationContext(), (int)suono);
                    mediaPlayer2.start();
                    bool = true;
                    mediaPlayer.release();
                    count ++;
                }
                runTime = System.currentTimeMillis() - start;
                System.out.println(runTime);
                if (runTime > 10000 && start != -1) {
                    if (count > 1) {
                        // 1. Instantiate an AlertDialog.Builder with its constructor
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                        // 2. Chain together various setter methods to set the dialog characteristics
                        builder.setMessage(R.string.dialog_message)
                                .setTitle(R.string.dialog_title);

                        // 3. Get the AlertDialog from create()
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        start = -1;
                        try {
                            FileOutputStream fos = new FileOutputStream(fileSave);
                            String s = "ciao";
                            fos.write(s.getBytes());
                            fos.close();
                        }
                        catch (Exception e){

                        }
                        cambio.setVisibility(View.VISIBLE);
                        count = 0;
                        runTime = 0;
                    }
                    else {
                        start = -1;
                    }
                    runTime = 0;
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
