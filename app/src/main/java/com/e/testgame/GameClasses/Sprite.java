package com.e.testgame.GameClasses;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.e.testgame.GameEngine.GameView;
import com.e.testgame.MathClasses.Vector2f;

public class Sprite {

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public Vector2f getOrigin() {
        return origin;
    }

    public void setOrigin(Vector2f origin) {
        this.origin = origin;
        startingPositions[0] = new Vector2f(origin.getX(),origin.getY());
    }

    public Vector2f[] getStartingPositions() {
        return startingPositions;
    }


    private Vector2f position;
    private Vector2f velocity;
    private Vector2f origin;
    private Vector2f[] startingPositions;
    private Bitmap bmp;
    private Sprite rightCopy;
    private Sprite leftCopy;
    private boolean parallaxEffect, stretchToScreen;
    private Grid grid;


    public Sprite(Bitmap bmp, Vector2f pos, Vector2f origin, Vector2f vel, boolean parallaxEffect, boolean stretchToScreen, Grid grid) {
        this.bmp = bmp;
        this.grid = grid;
        this.stretchToScreen = stretchToScreen;
        this.origin = origin;
        position = pos;
        startingPositions = new Vector2f[]{new Vector2f(origin.x,origin.y), new Vector2f(pos.x,pos.y)};
        velocity = vel;
        this.parallaxEffect = parallaxEffect;

    }
    /** Slowly scrolls sprites in one direction and moves the sprite to the opposite end of the screen if it is out of view */
    public void update() {

        position.x += velocity.x;
        origin.x += velocity.x;

        if (parallaxEffect) {

           if(!stretchToScreen) {
               parallaxEffect();
               return;
           }
            rightCopy.update();
            leftCopy.update();
            parallaxEffect();
        }

    }
    /** Initializes parallax effect: creates two copies of the
    sprite and and puts on the sides of the screen  */
    public void parallaxEffectInit() {

        if(parallaxEffect && !stretchToScreen)
        {
            return;
        }
        if (parallaxEffect) {
            rightCopy = new Sprite(bmp, new Vector2f(2 * position.getX(), position.getY()),new Vector2f(position.getX(), 0), velocity,  false,stretchToScreen, grid);
            leftCopy = new Sprite(bmp, new Vector2f(0, position.getY()), new Vector2f(-position.getX(), 0), velocity,  false,stretchToScreen, grid);
        }

    }
    /** Makes the sprite scroll endlessly around the screen by moving sprites to the opposite end of the screen if it is out of view */
    private void parallaxEffect() {
        if (!parallaxEffect) {
            return;
        }

        if(!stretchToScreen)
        {
            if(velocity.x > 0) {

                if(this.origin.getX() >= grid.getScreenWidth())
                {
                    this.origin = new Vector2f(-bmp.getWidth(),startingPositions[0].getY());
                    this.position = new Vector2f(0,startingPositions[0].getY() + this.bmp.getHeight());

                }

            }
            else if((this.position.getX()) <= 0) {

                this.origin = new Vector2f(grid.getScreenWidth(),startingPositions[0].getY());
                this.position = new Vector2f(grid.getScreenWidth()+bmp.getWidth(),startingPositions[1].getY());
            }
            return;
        }


      if(velocity.x > 0) {
          if (leftCopy.getOrigin().getX() >= startingPositions[0].getX()) {
              resetToStartingPoint();

          }
      }
      else if (rightCopy.getOrigin().getX() <= startingPositions[0].getX()) {
          resetToStartingPoint();
      }
    }


    private void resetToStartingPoint() {

        rightCopy.setOrigin(new Vector2f(rightCopy.getStartingPositions()[0].getX(),rightCopy.getStartingPositions()[0].getY()));
        rightCopy.setPosition(new Vector2f(rightCopy.getStartingPositions()[1].getX(),rightCopy.getStartingPositions()[1].getY()));

        leftCopy.setOrigin(new Vector2f(leftCopy.getStartingPositions()[0].getX(),leftCopy.getStartingPositions()[0].getY()));
        leftCopy.setPosition(new Vector2f(leftCopy.getStartingPositions()[1].getX(),leftCopy.getStartingPositions()[1].getY()));

        this.origin = new Vector2f(startingPositions[0].getX(),startingPositions[0].getY());
        this.position = new Vector2f(startingPositions[1].getX(),startingPositions[1].getY());

    }


    public void draw(Canvas canvas) {

        if (parallaxEffect && stretchToScreen) {
            //if(rightCopy.getPosition().x < grid.getScreenWidth() &&  rightCopy.getPosition().x > 0)
            //rightCopy.draw(canvas);

            //if(leftCopy.getPosition().x < grid.getScreenWidth() &&  leftCopy.getPosition().x > 0)
                //leftCopy.draw(canvas);

        }

        canvas.drawBitmap(bmp, null,  new Rect((int) origin.getX(), (int) origin.getY(), (int) position.getX(), (int) position.getY()), null);


    }
}
