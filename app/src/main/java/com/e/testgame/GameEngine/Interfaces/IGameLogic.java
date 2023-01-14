package com.e.testgame.GameEngine.Interfaces;


import android.graphics.Canvas;
import android.view.SurfaceView;

import com.e.testgame.GameEngine.GameView;



public interface IGameLogic {

    void init(GameView view) throws Exception;

    void update(SurfaceView view, float elapsedTime);

    void draw(Canvas canvas);


}