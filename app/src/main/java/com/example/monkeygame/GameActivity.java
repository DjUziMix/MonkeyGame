package com.example.monkeygame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;

public class GameActivity extends AppCompatActivity {
    private GameView gameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getting display size
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        gameView = new GameView(this, size.x, size.y);
        setContentView(gameView);
    }
    //methods for pausing and resuming the GameThread in GameView
    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(GameActivity.this, EndActivity.class);
        startActivity(intent);
    }
}