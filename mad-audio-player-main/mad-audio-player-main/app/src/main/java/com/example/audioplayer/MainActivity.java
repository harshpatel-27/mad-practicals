package com.example.audioplayer;

import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnPlay, btnPause, btnStop;
    MediaPlayer mediaPlayer;
    boolean isPaused = false; // to track pause state

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPlay = findViewById(R.id.btnPlay);
        btnPause = findViewById(R.id.btnPause);
        btnStop = findViewById(R.id.btnStop);

        // Load audio file from raw folder
        mediaPlayer = MediaPlayer.create(this, R.raw.song);

        // Play Button
        btnPlay.setOnClickListener(v -> {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                isPaused = false;
                btnPause.setText("Pause"); // reset button text
                Toast.makeText(this, "Playing Audio", Toast.LENGTH_SHORT).show();
            }
        });

        // Pause/Resume Button
        btnPause.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                isPaused = true;
                btnPause.setText("Resume"); // change text
                Toast.makeText(this, "Audio Paused", Toast.LENGTH_SHORT).show();
            } else if (isPaused) {
                mediaPlayer.start();
                isPaused = false;
                btnPause.setText("Pause"); // change back
                Toast.makeText(this, "Audio Resumed", Toast.LENGTH_SHORT).show();
            }
        });

        // Stop Button
        btnStop.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying() || isPaused) {
                mediaPlayer.stop();
                mediaPlayer = MediaPlayer.create(this, R.raw.song); // Reset
                isPaused = false;
                btnPause.setText("Pause"); // reset text
                Toast.makeText(this, "Audio Stopped", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }
}