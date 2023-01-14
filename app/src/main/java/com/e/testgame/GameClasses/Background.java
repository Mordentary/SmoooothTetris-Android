package com.e.testgame.GameClasses;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.e.testgame.GameEngine.GameView;
import com.e.testgame.MathClasses.Vector2f;
import com.e.testgame.R;

import java.util.ArrayList;
import java.util.List;

public class Background {

    private ArrayList<Sprite> sprites;
    private GameView gameView;
    private Grid grid;

    public Background(GameView gameView, Grid grid) {
        sprites = new ArrayList<>();
        this.gameView = gameView;
        this.grid = grid;
        loadSprites();
    }

    private void loadSprites() {
        Bitmap bmp1 = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.stars01);
        Bitmap bmp2 = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.stars02);
        Bitmap bmp3 = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.planet01);
        Bitmap bmp4 = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.planet02);
        Bitmap bmp5 = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.mountain01);


        Sprite bkg1 = new Sprite(bmp1,new Vector2f(grid.getScreenWidth(),grid.getScreenHeight()),new Vector2f(0,0),new Vector2f(0.1f,0),true, true,grid);
        Sprite bkg2 = new Sprite(bmp2,new Vector2f(grid.getScreenWidth(),grid.getScreenHeight()), new Vector2f(0,0),new Vector2f(-0.2f,0), true, true,grid);
        Sprite bkg3 = new Sprite(bmp3, new Vector2f(grid.getScreenWidth()/2,grid.getScreenHeight()/2),new Vector2f(grid.getScreenWidth()/2 - bmp3.getWidth(),grid.getScreenHeight()/2 -  bmp3.getHeight()),new Vector2f(-0.5f,0), true, false,grid);
        Sprite bkg4 = new Sprite(bmp4,new Vector2f(grid.getScreenWidth()/2,grid.getScreenHeight()/2),new Vector2f(grid.getScreenWidth()/2 - bmp3.getWidth(),grid.getScreenHeight()/2 -  bmp3.getHeight()),new Vector2f(2,0),true, false,grid);
        Sprite bkg5 = new Sprite(bmp5, new Vector2f(grid.getScreenWidth(),grid.getScreenHeightSeg()*(grid.getMaxNumOfRow()-1)),new Vector2f(0,grid.getScreenHeightSeg()*(grid.getMaxNumOfRow()-1)-bmp5.getHeight()),new Vector2f(0f,0), false, false,grid);

        sprites.add(bkg1);
        sprites.add(bkg2);
        sprites.add(bkg3);
        sprites.add(bkg4);
        sprites.add(bkg5);


        initAllParallaxFX();
    }
 /** Initialise Parallax Effect in all sprites related to background */
    private void initAllParallaxFX()
    {
        for(Sprite _sprite : sprites)
        {
            _sprite.parallaxEffectInit();
        }
    }

    public void update() {
        for(Sprite _sprite : sprites)
        {
            _sprite.update();
        }
    }

    public void draw(Canvas canvas) {
       for(Sprite _sprite : sprites)
       {
           _sprite.draw(canvas);
       }
    }
}
