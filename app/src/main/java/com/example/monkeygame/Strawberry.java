package com.example.monkeygame;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class Strawberry extends Common{
    Random random;
    //constructor
    public Strawberry(Activity activity, int sizeX, int sizeY){
        super(activity, sizeX, sizeY);

        //the photo
        bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.strawberry);

        //число, което използваме за х на ягодата, т.е. да пада от различна позиция с фиксиран у
        random = new Random();

        collisionDetection = new Rect(x + 1, y + 1, x + bitmap.getWidth()-1, y + bitmap.getHeight()-1);
        Positioning();
    }


    //първоначалното позициониране при извикване на конструктора
    private void Positioning() {
        x = random.nextInt(sizeX - bitmap.getWidth());
        y = 0 - bitmap.getHeight();
        speed = 3;
    }

    //Мотод за Update на ягодата
    public void Update(){
        //примяна на позициата по у със стойността в speed
        y += speed;

        //сетваме collisiondetection-a
        collisionDetection.bottom = y + bitmap.getHeight()-1;
        collisionDetection.top = y+1;
        collisionDetection.left = x+1;
        collisionDetection.right = x + bitmap.getWidth()-1;

    }
    //метод, който използваме, когато евентуално player изпусне ягодата
    //правим отново Positioninig
    public void resetPosition(){
        x = random.nextInt(sizeX - bitmap.getWidth());
        y = 0 - bitmap.getHeight();
    }
}
