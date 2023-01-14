package com.e.testgame.GameClasses;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.e.testgame.GameEngine.GameView;
import com.e.testgame.MathClasses.AABB;
import com.e.testgame.MathClasses.Vector2f;
import com.e.testgame.R;

import java.util.ArrayList;

public class Foreground {


    private ArrayList<Sprite> sprites;
    private GameView gameView;
    private Grid grid;
    private AABB floor;

    public Foreground(GameView gameView, Grid grid, AABB floor) {
        this.floor = floor;
        sprites = new ArrayList<>();
        this.gameView = gameView;
        this.grid = grid;
        loadSprites();
    }
    private void loadSprites() {
            Bitmap bmp1 = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.foreground);
     //   Bitmap bmp2 = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.stars02);

        System.out.println(floor.max);
        System.out.println(floor.min);
            Sprite bkg1 = new Sprite(bmp1, new Vector2f(floor.max.getX(),floor.max.getY()),new Vector2f(floor.min.x,floor.min.y - grid.getScreenHeightSeg()/2), new Vector2f(0f,0), false, false,grid);

        //      Sprite bkg2 = new Sprite(bmp2,new Vector2f(grid.getScreenWidth(),grid.getScreenHeight()), new Vector2f(-0.2f,0), true, true,grid);

        System.out.println(bkg1.getPosition());
        System.out.println(bkg1.getOrigin());
        sprites.add(bkg1);

    }

    public void draw(Canvas canvas) {
        for(Sprite _sprite : sprites)
        {
            _sprite.draw(canvas);
        }
    }
}
