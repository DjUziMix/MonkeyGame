package com.example.monkeygame;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;

public class Common {
    //за общите части
    Activity activity;
    Bitmap bitmap;
    int x;
    int y;
    int sizeX;
    int sizeY;

    int speed = 1;
    Rect collisionDetection;

    public Common(Activity activity, int sizeX, int sizeY) {
        this.activity = activity;

        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }
}
