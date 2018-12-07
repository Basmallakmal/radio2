package com.aladziviesoft.radio;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    Button playbutton;
    boolean prepared = false;
    ProgressBar sekkbar;
    TextView tanda;

    public void playMusic(View view) throws IOException {
        playbutton = (Button) findViewById(R.id.playbutton);
        playPause();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tanda = (TextView) findViewById(R.id.tanda);
        tanda.setVisibility(View.INVISIBLE);

        sekkbar = (ProgressBar) findViewById(R.id.sekbar);
        sekkbar.setMax(100);
        sekkbar.setVisibility(View.INVISIBLE);
        sekkbar.setIndeterminate(true);


        mediaPlayer = new MediaPlayer();
        String url = "http://live.radiorodja.com:80/;stream.mp3?_=3";

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mediaPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mediaPlayer) {
                prepared = true;
            }
        });

        mediaPlayer.setOnBufferingUpdateListener(new OnBufferingUpdateListener() {
            public void onBufferingUpdate(MediaPlayer mp,int percent) {
                sekkbar.setIndeterminate(false);
                sekkbar.setSecondaryProgress(100);
                Log.i("Buffering", "" + percent);
            }
        });

    }


    public void playPause() {
        if (!mediaPlayer.isPlaying() && prepared) {
            mediaPlayer.start();
            playbutton.setBackgroundResource(R.drawable.stop);
            sekkbar.setVisibility(View.VISIBLE);
            tanda.setVisibility(View.VISIBLE);
        } else if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            playbutton.setBackgroundResource(R.drawable.next);
            sekkbar.setIndeterminate(true);
            sekkbar.setVisibility(View.INVISIBLE);
            tanda.setVisibility(View.INVISIBLE);
        }
    }

}
