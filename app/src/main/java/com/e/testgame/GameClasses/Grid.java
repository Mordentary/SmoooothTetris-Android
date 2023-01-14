package com.e.testgame.GameClasses;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Grid {

    private int screenHeight, screenWidth;
    private float screenWidthSeg;
    private float screenHeightSeg;
    private final int maxNumOfColumn;
    private final int maxNumOfRow;


    public Grid(int height, int width, int maxColumn) {

        screenHeight = height;
        screenWidth = width;

        maxNumOfColumn = maxColumn;
        screenWidthSeg = width / maxNumOfColumn;

        maxNumOfRow = (int)(screenHeight / screenWidthSeg);
        screenHeightSeg = screenHeight / (screenHeight / screenWidthSeg);

    }


    public int getMaxNumOfColumn() {
        return maxNumOfColumn;
    }

    public int getMaxNumOfRow() {
        return maxNumOfRow;
    }

    public float getScreenHeightSeg() {
        return screenHeightSeg;
    }

    public float getWidthSeg() {
        return screenWidthSeg;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    /* Draw grid on the canvas*/
    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        paint.setTextSize(100);

     for (int i = screenWidth / maxNumOfColumn; i < screenWidth; i += screenWidth / maxNumOfColumn) {
            canvas.drawLine(i, 0, i, screenHeight, paint);
        }
        for (float i = screenHeightSeg; i < screenHeight; i += screenHeightSeg) {
            canvas.drawLine(0, i, screenWidth, i, paint);
        }

    }
}
