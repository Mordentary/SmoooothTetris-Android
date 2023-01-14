package com.e.testgame.GameEngine;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.e.testgame.GameEngine.GameView;

import java.util.concurrent.TimeUnit;

public class MainThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean running;
    private static Canvas canvas;
    private final int targetFPS = 60;



    public MainThread(SurfaceHolder surfaceHolder, GameView gameView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    public void setRunning(boolean isRunning) {
        running = isRunning;
    }


    /**
     * Starts the main loop of the game. (I'm using fixed time step loop with synchronization, that means deltaTime is fixed and also that graphics and physics update calls are separated)
     */
    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double nsPerTick = 1_000_000_000/targetFPS;
        double accumulator = 0;
        int frames = 0;
        int ticks = 0;

        while (running) {
            canvas = null;
            long now = System.nanoTime();
            accumulator += (now-lastTime)/nsPerTick;
            boolean shouldRender = false;
            while (accumulator >= 1) {
                ticks++;
                gameView.update((float)(System.currentTimeMillis() - timer)/1000/30);
                accumulator -= 1;
                shouldRender = true;
            }

            if (shouldRender) {
                frames++;
                try {
                    canvas = this.surfaceHolder.lockCanvas();
                    synchronized(surfaceHolder) {
                        this.gameView.draw(canvas);
                    }
                } catch (Exception e) {       }
                finally {
                    if (canvas != null)            {
                        try {
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            if (System.currentTimeMillis()  - timer >= 1000) {
                timer += 1000;
                System.out.println("Ticks: " + ticks + ", Frames: " + frames);
                ticks = 0;
                frames = 0;
            }
            lastTime = now;
        }
    }

}

