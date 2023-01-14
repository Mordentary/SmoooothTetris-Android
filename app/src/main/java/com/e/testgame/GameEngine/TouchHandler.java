package com.e.testgame.GameEngine;

import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import com.e.testgame.GameClasses.Grid;
import com.e.testgame.MathClasses.Vector2f;

public class TouchHandler implements View.OnTouchListener {


    private Vector2f position;
    private Grid grid;

    public TouchHandler(SurfaceView view, Grid _grid) {
        grid = _grid;
        position = new Vector2f();
        view.setOnTouchListener(this);
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }


    /**
     * Checks which part of the screen the user has touched. There are 4 parts: upper left lower left, lower right,
     * right top.
     */
    public int CheckPartScreen() {
        // 0 = left top corner
        // 1 = right top corner
        // 2 = left bottom corner
        // 3 = right bottom corner
        if (position.x != 0 && position.y != 0) {
            if (position.x / (grid.getScreenWidth()/2) >= 1) {

                if (position.y / (grid.getScreenHeight()/2) >= 1) {
                    return 3;
                }
                return 1;
            }
            if (position.y / (grid.getScreenHeight()/2) >= 1) {
                return 2;
            }
            return 0;


        }
        return -1;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        position = new Vector2f(event.getX(), event.getY());
        return false;

    }

}
