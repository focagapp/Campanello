package com.campanello.focaga.campanello;

import android.app.Activity;
import android.app.AlertDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends Activity {
    Button campanello;
    MediaPlayer mediaPlayer = new MediaPlayer();
    MediaPlayer mediaPlayer2 = new MediaPlayer();
    Boolean jumpWhenPlaying = true;
    Switch cambio;
    Object suono = R.raw.bell;
    int count;
    long start = -1;
    long runTime = -1;
    public static File fileSave;
    //private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: remove the comment when application will be online + add unitId in xml
        /*MobileAds.initialize(getApplicationContext(),
                "");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/

        campanello = (Button) findViewById(R.id.campanello);
        cambio = (Switch) findViewById(R.id.cambio);
        count = 0;

        fileSave = new File(this.getFilesDir(), "vittoria.txt");
        if (fileSave.exists()) {
            cambio.setVisibility(View.VISIBLE);
        }

        campanello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //only when the cameriere is unlocked
                if (start == -1 && !fileSave.exists()) {
                    start = System.currentTimeMillis();
                }

                //manage the memory problem for the media player object
                if (jumpWhenPlaying) {
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), (int)suono);
                    mediaPlayer.start();
                    jumpWhenPlaying = !jumpWhenPlaying;
                    mediaPlayer2.release();
                    count ++;
                } else {
                    mediaPlayer2 = MediaPlayer.create(getApplicationContext(), (int)suono);
                    mediaPlayer2.start();
                    jumpWhenPlaying = !jumpWhenPlaying;
                    mediaPlayer.release();
                    count ++;
                }

                //difference between the time of the first click and the current time
                runTime = System.currentTimeMillis() - start;
                //if you already won, start wibb be always -1
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
                        //reset the counter and save your win
                        start = -1;
                        try {
                            FileOutputStream fos = new FileOutputStream(fileSave);
                            String unlocked = "unlocked";
                            fos.write(unlocked.getBytes());
                            fos.close();
                        } catch (Exception e) {

                        }
                        cambio.setVisibility(View.VISIBLE);
                        count = 0;
                        runTime = 0;
                    }
                    runTime = 0;
                }
            }
        });

        //set the sound in relation of the switch (event handler when switch status changes)
        cambio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    cambio.setText("Cameriere");
                    suono = R.raw.cameriere;
                } else {
                    cambio.setText("Ding");
                    suono = R.raw.bell;
                }
            }
        });
    }

    //lock return button
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
