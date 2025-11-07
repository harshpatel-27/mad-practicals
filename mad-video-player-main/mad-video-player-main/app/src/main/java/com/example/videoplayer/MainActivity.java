package com.example.videoplayer;

import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    VideoView videoView;
    Button btnPlay, btnPause, btnStop;
    MediaController mediaController;
    boolean isPaused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = findViewById(R.id.videoView);
        btnPlay = findViewById(R.id.btnPlay);
        btnPause = findViewById(R.id.btnPause);
        btnStop = findViewById(R.id.btnStop);

        // Load video from raw folder
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video);
        videoView.setVideoURI(videoUri);

        // Media Controller
        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        // Play
        btnPlay.setOnClickListener(v -> {
            if (!videoView.isPlaying()) {
                videoView.start();
                isPaused = false;
                btnPause.setText("Pause");
                Toast.makeText(this, "Video Playing", Toast.LENGTH_SHORT).show();
            }
        });

        // Pause/Resume
        btnPause.setOnClickListener(v -> {
            if (videoView.isPlaying()) {
                videoView.pause();
                isPaused = true;
                btnPause.setText("Resume");
                Toast.makeText(this, "Video Paused", Toast.LENGTH_SHORT).show();
            } else if (isPaused) {
                videoView.start();
                isPaused = false;
                btnPause.setText("Pause");
                Toast.makeText(this, "Video Resumed", Toast.LENGTH_SHORT).show();
            }
        });

        // Stop
        btnStop.setOnClickListener(v -> {
            if (videoView.isPlaying() || isPaused) {
                videoView.stopPlayback();
                videoView.setVideoURI(videoUri); // reset
                isPaused = false;
                btnPause.setText("Pause");
                Toast.makeText(this, "Video Stopped", Toast.LENGTH_SHORT).show();
            }
        });
    }
}