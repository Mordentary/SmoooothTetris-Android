package com.e.testgame.GameClasses;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.e.testgame.DBHelper;
import com.e.testgame.GameClasses.src.BlockDeleter;
import com.e.testgame.GameClasses.src.Figure;
import com.e.testgame.GameEngine.GameView;
import com.e.testgame.MainActivity;
import com.e.testgame.MathClasses.OBB;
import com.e.testgame.RestartActivity;

import java.util.ArrayList;

public class UI {


    public boolean isGameOver() {
        return gameOver;
    }


    private int numOfFigure;
    private Paint paint;
    private boolean gameOver;
    private GameView view;
    public int finalScore;

    public UI(GameView view) {
        this.view = view;
        this.finalScore = 0;
        paint = new Paint();
        paint.setTextSize(50);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
    }

    /**
     * Checks if the figure has reached the top
     */
    private void checkOnUpperLimit(ArrayList<Figure> allFigure) {
        for (Figure figure : allFigure) {
            if (figure.isGrounded() && figure != null) {
                for (OBB block : figure.getFigureBlocks()) {
                    if (block != null && (block.getNumRow() - 1) == 0) {

                        gameOver = true;

                    }
                }
            }
        }
    }

    public void draw(ArrayList<Figure> allFigure, Canvas canvas, Grid grid) {

        checkOnUpperLimit(allFigure);
        setGameOver(allFigure, canvas, grid);

    }

    private void clearAllFigure(ArrayList<Figure> allFigure) {
        if (!allFigure.isEmpty()) {
            allFigure.clear();
        }
    }

    /**
     * If game state is not <gameOver> than draws interface(Counter of figures, Score) otherwise destroy surface of canvas and sends final scores to a
     * intermediate database(I'm calling it TinyDB :^) )
     */
    private void setGameOver(ArrayList<Figure> allFigure, Canvas canvas, Grid grid) {
        if (gameOver) {
            Rect _rect = new Rect(0, 0, grid.getScreenWidth(), (int) grid.getWidthSeg() * 2);
            paint.setColor(Color.BLACK);
            canvas.drawRect(_rect, paint);
            paint.setColor(Color.rgb(65, 105, 225));
            paint.setStrokeWidth(5);
            paint.setTextSize(grid.getScreenWidth() / 20);
            canvas.drawText("Number of figures: " + numOfFigure, grid.getWidthSeg() / 2, (int) grid.getScreenHeightSeg(), paint);
            canvas.drawText("YOUR SCORE: " + finalScore, grid.getWidthSeg() * 7, (int) grid.getScreenHeightSeg(), paint);
            clearAllFigure(allFigure);
            RestartActivity.score = finalScore;
            view.surfaceDestroyed(view.getHolder());

        } else {
            Rect _rect = new Rect(0, 0, grid.getScreenWidth(), (int) grid.getWidthSeg() * 2);
            paint.setColor(Color.BLACK);
            canvas.drawRect(_rect, paint);
            paint.setColor(Color.rgb(65, 105, 225));
            paint.setStrokeWidth(5);
            paint.setTextSize(grid.getScreenWidth() / 20);
            canvas.drawText("Number of figures: " + numOfFigure, grid.getWidthSeg() / 2, (int) grid.getScreenHeightSeg() * 1, paint);
            canvas.drawText("YOUR SCORE: " + finalScore, grid.getWidthSeg() * 7, (int) grid.getScreenHeightSeg() * 1, paint);
        }
    }

    /**
     * Counts score
     */
    public void scoring(ArrayList<Figure> allFigure, Grid grid) {
        if (BlockDeleter.findFullRow(allFigure, grid) != -1) {
            finalScore += 100;
        }
    }

    public void updateNumOfFigure(int numFigure) {
        this.numOfFigure = numFigure;
    }
}
