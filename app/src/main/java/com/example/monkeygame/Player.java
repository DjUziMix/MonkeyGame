package com.example.monkeygame;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Player extends Common{
    String direction = "";
    int speed1 = 5;
    //constructor
    public Player(Activity activity, int sizeX, int sizeY){
        super(activity, sizeX, sizeY);

        //the photo
        bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.biggermonkey);

        //where to be set the photo
        x = sizeX / 2 - bitmap.getWidth() / 2;
        y = (sizeY / 2 - bitmap.getHeight() / 2) + 200;


        collisionDetection = new Rect(x + 1, y + 1, x + bitmap.getWidth()-1, y + bitmap.getHeight()-1);
    }


    public void Update(){

        //depends on the velocity in GameView
        switch (direction){

            case "left":
                //върви по х наляво
                x -= speed1;
                break;
            case "right":
                //променя х, увеличавайки го, т.е. върви надясно
                x += speed1;
                break;
            case "":
                break;
        }

        //ако излезне от екрана съотвено от лево или от дясно да се появява от другата страна
        if (x < 0 - bitmap.getWidth()){
            x = sizeX;
        }else if(x > sizeX) {
            x = 0 - bitmap.getWidth();
        }

        //сетваме colliosionDetection-a
        collisionDetection.bottom = y + bitmap.getHeight()-1;
        collisionDetection.top = y+1;
        collisionDetection.left = x+1;
        collisionDetection.right = x + bitmap.getWidth()-1;
    }

}

