package com.example.monkeygame;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class Bad extends Common{
    Random random;

    //constructor
    public Bad(Activity activity, int sizeX, int sizeY){
        super(activity, sizeX, sizeY);

        //the photo
        bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.coconut);

        //число, което използваме за х на ягодата, т.е. да пада от различна позиция с фиксиран у
        random = new Random();

        collisionDetection = new Rect(x + 1, y + 1, x + bitmap.getWidth()-1, y + bitmap.getHeight()-1);
        Positioning();
    }

    //////////////////
    //////////////////////////////same as in Strawberry
    private void Positioning() {
        x = random.nextInt(sizeX - bitmap.getWidth());
        y = 0 - bitmap.getHeight();
        speed = 5;
    }
    public void Update(){
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
