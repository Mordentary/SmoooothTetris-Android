package com.e.testgame.GameClasses;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceView;
import android.view.View;


import com.e.testgame.GameClasses.src.BlockDeleter;
import com.e.testgame.GameClasses.src.Figure;
import com.e.testgame.GameEngine.GameView;
import com.e.testgame.GameEngine.TouchHandler;
import com.e.testgame.GameEngine.Interfaces.IGameLogic;
import com.e.testgame.GameEngine.Physics2D.Physics2D;
import com.e.testgame.MathClasses.AABB;
import com.e.testgame.MathClasses.MathHelper;
import com.e.testgame.MathClasses.OBB;
import com.e.testgame.MathClasses.Vector2f;


import java.util.ArrayList;

public class MainGame implements IGameLogic {

    public UI _ui;
    private Background _background;
    private Foreground _foreground;
    private TouchHandler _touch;
    private Paint _paint;
    private Physics2D _physics2D;

    private Vector2f _gravity;
    private Rect _rect;
    private AABB floor;
    private OBB ref;
    private int surfHeight, surfWidth;
    private Grid _grid;
    private Figure currentFigure, firstFigure;
    private ArrayList<Figure> figures;


    /** Initializes all objects, loads sprites, etc. */
    @Override
    public void init(GameView view) throws Exception {

        surfWidth = view.getHolder().getSurfaceFrame().width();

        surfHeight = view.getHolder().getSurfaceFrame().height();

        _grid = new Grid(surfHeight, surfWidth, 12);

        _touch = new TouchHandler(view, _grid);
        _paint = new Paint();

        _ui = new UI(view);

        _gravity = new Vector2f(0, 0.009807f ); //0.009807f

        _physics2D = new Physics2D(0.167f, _gravity);

        figures = new ArrayList<>();

        ref = new OBB((_grid.getMaxNumOfColumn() / 2) - 1, 1, (int) _grid.getWidthSeg(), _grid.getScreenHeightSeg(), _grid.getMaxNumOfColumn(), 0);

        floor = new AABB(new Vector2f(0, _grid.getScreenHeightSeg()*(_grid.getMaxNumOfRow()-1)), new Vector2f(_grid.getScreenWidth(), _grid.getScreenHeight()));

        firstFigure = new Figure(ref, _physics2D, MathHelper.randomCharForFigure());

        currentFigure = firstFigure;

        figures.add(currentFigure);

        _paint.setStyle(Paint.Style.FILL);
        _paint.setAntiAlias(true);
        _paint.setDither(true);

        _background = new Background(view,_grid);
        _foreground = new Foreground(view,_grid, floor);
        

    }
    /** Updates all objects in a game and handles user input every frame  */
    @Override
    public void update(SurfaceView view, float elapsedTime) {


        if (figures.size() < 100 && figures.size() != 0 && currentFigure.isGrounded()) {
            currentFigure = new Figure(ref, _physics2D,MathHelper.randomCharForFigure());
            figures.add(currentFigure);
        }
        if (_touch.CheckPartScreen() == 0) {
            _touch.setPosition(new Vector2f());


            if (currentFigure.preventFigureRotation(figures, "left") &&  !currentFigure.isFigureRotating()) {
                currentFigure.correctPosition("left");
                currentFigure.figureRotate("left");
            }

        }
        if (_touch.CheckPartScreen() == 1) {
            _touch.setPosition(new Vector2f());

            if (currentFigure.preventFigureRotation(figures, "right") &&  !currentFigure.isFigureRotating()) {
                currentFigure.correctPosition("right");
                currentFigure.figureRotate("right");
            }

        }
        if (_touch.CheckPartScreen() == 2) {
            _touch.setPosition(new Vector2f());

            if (currentFigure.preventFigureTranslation(figures, "left") && !currentFigure.isFigureRotating()) {
                currentFigure.figureTranslate("left");
            }


        }
        if (_touch.CheckPartScreen() == 3) {
            _touch.setPosition(new Vector2f());

            if (currentFigure.preventFigureTranslation(figures, "right") && !currentFigure.isFigureRotating()) {
                currentFigure.figureTranslate("right");
            }
        }
        _physics2D.fixedUpdate();
        for (Figure f : figures) {
            f.update(figures, floor);
         }

        _ui.scoring(figures, _grid);

        BlockDeleter.deleteBlocks(figures, _grid);

        // f1.printNumOfColumn();
        for (Figure f : figures) {
            if (f != null && !f.isGrounded()) {
                //  f.printCenters();
            }
        }

        _background.update();
        _ui.updateNumOfFigure(figures.size());

    }
    /** Draw every drawable object on a canvas */
    @Override
    public void draw(Canvas canvas) {

        _background.draw(canvas);

        for (Figure f : figures) {
            if (f != null) {
                f.draw(canvas);
            }
        }

        _ui.draw(figures, canvas, _grid);
        _foreground.draw(canvas);
    }
}
