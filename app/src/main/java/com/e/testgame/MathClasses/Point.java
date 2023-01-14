package com.e.testgame.MathClasses;

public class Point {

    private float x;
    private float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public void subtractX(float x)
    {
        this.x -= x;
    }
    public void addX(float x)
    {
        this.x += x;
    }
    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }
    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float Get_Distance(Point P) {
        return (float)Math.sqrt(Math.pow(this.getX() - P.getX(), 2) + Math.pow(this.getY() - P.getY(), 2));
    }

}
