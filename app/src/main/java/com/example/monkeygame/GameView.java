package com.example.monkeygame;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.VelocityTracker;

import java.util.Random;

public class GameView extends SurfaceView implements Runnable {
    //photo and rect for the background
    Bitmap background;
    Rect rectBackground;
    //boolean varible for the run method
    //if it is true the while() continues to update, draw and refresh
    boolean isRunnning = true;

    //display size
    int screenSizeX;
    int screenSizeY;

    Player player;
    Random random = new Random();

    //tools that we need to draw and white on the screen
    SurfaceHolder surfaceHolder;
    Canvas canvas;
    Paint paint;
    Thread gameThread;

    //2 objects of Banana and Apple types
    Banana banana;
    Apple apple;
    //Banana[] arr; -> 2 начин с масив от Banana objects
    //int arrayLength = 1;

    //1 object from Bad type - Coconuts
    Bad coconut;
    //1 object from Strawberry type
    Strawberry strawberry;

    //inner counter for levels
    int count;
    int score;
    //thread for pausing and resuming methods
    private Thread thread;
    Activity activity;

    //метод, който генерира нивото, тоест слага background
    private void GenerateLevel(int screenSizeX, int screenSizeY){
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        rectBackground = new Rect(0,0,screenSizeX, screenSizeY);
    }
    //constructor
    public GameView(Activity activity, int screenSizeX, int screenSizeY) {
        super(activity);
        this.screenSizeX = screenSizeX;
        this.screenSizeY = screenSizeY;

        this.activity = activity;
        //initialize the Player
        player = new Player(activity, screenSizeX, screenSizeY);

        banana = new Banana(activity, screenSizeX, screenSizeY);
        apple = new Apple(activity, screenSizeX, screenSizeY);
//        the second way with the array
//        arr = new Banana[2];
//        arr[0] = new Banana(activity, screenSizeX, screenSizeY);
//        arr[1] = new Banana(activity, screenSizeX, screenSizeY);


        coconut = new Bad(activity, screenSizeX,screenSizeY);
        strawberry = new Strawberry(activity, screenSizeX, screenSizeY);
        count = 0;
        score = 0;

        GenerateLevel(screenSizeX,screenSizeY);
        surfaceHolder = getHolder();
        paint = new Paint();

        gameThread = new Thread(this);
        gameThread.start();

    }

    //Overrided method from Runnable interface
    //докато isRunning e true -> update, draw and refresh
    @Override
    public void run() {
        while(isRunnning){
            update();
            draw();
            //на 3 и на 10 на вътрешния брояч gamethread заспива за 3000 милисек. за преминаване към следващо ниво
            if (count == 3 || count == 10){
                //stop for photo
                StopPhoto();
            }else{
                refreshRate();
            }
        }
    }

    //refresh през 4 милисек.
    private void refreshRate() {
        try{
            gameThread.sleep(4);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }


    private void draw() {
        //ако е valid
        if (surfaceHolder.getSurface().isValid()){
            canvas = surfaceHolder.lockCanvas();

            //set the paint color and the canvas's
            paint.setColor(Color.RED);
            canvas.drawColor(Color.BLACK);

            //изрисуваме background
            canvas.drawBitmap(background, null, rectBackground,paint);
            canvas.drawBitmap(player.bitmap, player.x, player.y, paint);

            //ako count стане 3 преди run метода да стигне до StopPhoto(), се изчертава смешна снимка на екрана
            if (count == 3){
                Bitmap photo = BitmapFactory.decodeResource(getResources(), R.drawable.funny);
                PhotoDraw(photo);
            }
//second way -> array
//            for (int i = 0; i < arrayLength; i++) {
//                canvas.drawBitmap(arr[i].bitmap, arr[i].x, arr[i].y, paint);
//                if (arr[i].collisionDetection.intersect(player.collisionDetection)) {
//                    arr[i].resetPosition();
//                    count++;
//                    score++;
//                }
//
//                if (arr[i].y > screenSizeY) {
//                    arr[i].resetPosition();
//                }
//
//            }
//
//            if (count > 3 && arrayLength != 1){
//                arrayLength++;
//            }

            //изрисува си банана
            canvas.drawBitmap(banana.bitmap, banana.x, banana.y, paint);
            //ако се засекат с играча, вътрешния брояч и резултата се увеличават
            if (banana.collisionDetection.intersect(player.collisionDetection)) {
                count++;
                score++;
                banana.resetPosition();
            }
            //ако е излезнал извун екрана Position-a се ресетва
            if (banana.y > screenSizeY) {
                banana.resetPosition();
            }

            //same but after count > 3
            if (count > 3){
                canvas.drawBitmap(apple.bitmap, apple.x, apple.y, paint);
                if (apple.collisionDetection.intersect(player.collisionDetection)) {
                    count++;
                    score++;
                    apple.resetPosition();
                }

                if (apple.y > screenSizeY) {
                    apple.resetPosition();
                    //ако при resetPosition() обекта се намира върху друг, т.е. са на един и същи х,
                    // да се позиционира отново
                }

            }

            //same but after count > 6
            //освен, чe резултата тук се увеличава с 2
            if (count > 6){
                canvas.drawBitmap(strawberry.bitmap, strawberry.x, strawberry.y, paint);
                if (strawberry.collisionDetection.intersect(player.collisionDetection)) {
                    score = score + 2;
                    strawberry.resetPosition();
                    //ако при resetPosition() обекта се намира върху друг, т.е. са на един и същи х,
                    // да се позиционира отново
                    while (strawberry.collisionDetection.intersect(banana.collisionDetection)){
                        strawberry.resetPosition();
                    }
                }
                if (strawberry.y > screenSizeY) {
                    strawberry.resetPosition();
                    //ако при resetPosition() обекта се намира върху друг, т.е. са на един и същи х,
                    // да се позиционира отново
                    while (strawberry.collisionDetection.intersect(banana.collisionDetection)){
                        strawberry.resetPosition();
                    }
                }

            }

            //same but after count > 10
            //освен, чe резултата тук се намалява с 3
            if (count > 10){
                canvas.drawBitmap(coconut.bitmap, coconut.x, coconut.y, paint);
                if (coconut.collisionDetection.intersect(player.collisionDetection)) {
                    score = score - 3;
                    coconut.resetPosition();
                }
                if (coconut.y > screenSizeY) {
                    coconut.resetPosition();
                }

            }


            //ако count стане 10 минава следващо ниво (с кокоси) и отново изрисува смешна снимка на екрана
            if (count == 10){
                Bitmap photo = BitmapFactory.decodeResource(getResources(), R.drawable.mouse);
                PhotoDraw(photo);
            }

            //изписва постоянно резултата
            String scoreText = "Score: " + score;
            paint.setTextSize(35);
            canvas.drawText(scoreText, 50, (screenSizeY / 2) - 350, paint);

            //unlock the canvas to draw on the surfaceview
            surfaceHolder.unlockCanvasAndPost(canvas);

        }
    }

    //метод за изчаване на смешната снимката
    private void StopPhoto() {
        //спира изпълнението на run метода
        isRunnning = false;
        try{
            gameThread.sleep(3000);
            count++;
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }finally {
            isRunnning = true;
            run();
        }
    }

    //изрисуването на снимката, заедно с надпис
    private void PhotoDraw(Bitmap photo){
        String scoreText = "Next level!!!";
        paint.setTextSize(40);
        canvas.drawText(scoreText, 50, (screenSizeY / 2) - 270, paint);
        canvas.drawBitmap(photo,  screenSizeX / 2 - photo.getWidth() / 2, screenSizeY / 2 - photo.getHeight() / 2, paint);
    }


    private void update(){
        //update на всичко, най-вече по координатни точки
        player.Update();

//with array
//        for (int i = 0; i < arrayLength; i++) {
//            arr[i].Update();
//        }
        banana.Update();

        if (count > 3){
            apple.Update();
        }
        if (count>6){
            strawberry.Update();
        }
        if (count>10){
            coconut.Update();
        }

        //check if score is under 0
        //and if so, calls EndGame() method
        if (score < 0){
            EndGame();
        }
    }

    private void EndGame() {
        //stops GameThread
        isRunnning = false;
        activity.finish();
    }

    //velocity, което следи за допир до екрана
    VelocityTracker velocityTracker;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionIndex();
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                if (velocityTracker == null){
                    velocityTracker = VelocityTracker.obtain();
                }else{
                    velocityTracker.clear();
                }

                velocityTracker.addMovement(event);
                break;

            case MotionEvent.ACTION_MOVE:

                velocityTracker.addMovement(event);
                velocityTracker.computeCurrentVelocity(1000);

                float xVelocity = velocityTracker.getXVelocity();
                float yVelocity = velocityTracker.getYVelocity();

                if (Math.abs(xVelocity) > Math.abs(yVelocity)){
                    if (xVelocity > 0){
                        player.direction = "right";
                    }else {
                        player.direction = "left";
                    }
                }else{
                    if(yVelocity < 0){
                        player.direction = "left";
                    }
                    else {
                        player.direction = "right";
                    }
                }

                Log.wtf("moved", velocityTracker.getXVelocity() + "");
                break;

        }

        return true;
    }


    //за продължение на thread-a след паузиране(излиза от игарата)
    //т.е. ако излезнем внезапно от играта, когато влзенем тя да продължи от точката на излизане
    public void resume () {

        isRunnning = true;
        thread = new Thread(this);
        thread.start();

    }

    //pause method, който списа изпълнението на run
    public void pause () {
        try {
            isRunnning = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

