package com.example.monkeygame;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class Banana extends Common{

    int temp = 0;
    Random random;
    public Banana(Activity activity, int sizeX, int sizeY){
        super(activity, sizeX, sizeY);

        bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.banana);

        random = new Random();

        collisionDetection = new Rect(x + 1, y + 1, x + bitmap.getWidth()-1, y + bitmap.getHeight()-1);
        Positioning();
    }

    private void Positioning() {
        x = random.nextInt(sizeX - bitmap.getWidth());
        y = 0 - bitmap.getHeight();
        speed = 2;
    }

    public void Update(){
        temp++;
        if (temp == 6){
            speed += 1;
        }
        y += speed;

        collisionDetection.bottom = y + bitmap.getHeight()-1;
        collisionDetection.top = y+1;
        collisionDetection.left = x+1;
        collisionDetection.right = x + bitmap.getWidth()-1;

    }

    public void resetPosition(){

        x = random.nextInt(sizeX - bitmap.getWidth());
        y = 0 - bitmap.getHeight();
    }

}
