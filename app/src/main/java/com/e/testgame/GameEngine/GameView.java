package com.e.testgame.GameEngine;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.e.testgame.GameClasses.MainGame;
import com.e.testgame.GameEngine.Interfaces.IGameLogic;
import com.e.testgame.MainActivity;


public class GameView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private final MainThread thread;
    private final IGameLogic gameLogic;
    private final Canvas canvas;
    private MainActivity mainAcivity;
    boolean protection = false;


    public GameView(MainActivity context) {
        super(context);

        mainAcivity = context;
        getHolder().addCallback(this);
        this.gameLogic = new MainGame();
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
        setOnTouchListener(this);
        canvas = new Canvas();
        System.out.println("Hardware:" +  canvas.isHardwareAccelerated());
        
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            gameLogic.init(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        thread.setRunning(true);
        thread.start();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

            thread.setRunning(false);
        if(!protection) {
                mainAcivity.startGameOverMenu();
                protection = true;
            }
    }

    public void update(float elapsedTime) {
        gameLogic.update(this, elapsedTime);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            gameLogic.draw(canvas);
        }
    }

    public Canvas getCanvas() {
        return canvas;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        return false;
    }


}
