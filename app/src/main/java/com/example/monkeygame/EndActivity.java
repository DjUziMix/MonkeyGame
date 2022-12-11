package com.example.monkeygame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity {

    TextView bestScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        findViewById(R.id.tryagainButton).setOnClickListener(new View.OnClickListener(){
            //бутона Try Again ни води към MainActivity
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EndActivity.this, MainActivity.class));
            }
        });
    }
}