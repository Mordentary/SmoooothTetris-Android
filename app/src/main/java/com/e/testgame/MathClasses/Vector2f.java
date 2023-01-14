package com.e.testgame.MathClasses;

public class Vector2f
{
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float x;
    public float y;

    public Vector2f(float x, float y)
    {
       this.x = x;
       this.y = y;
    }
    public Vector2f(Vector2f v)
    {
        this.x = v.x;
        this.y = v.y;
    }

    public Vector2f()
    {
        this.x = 0f;
        this.y = 0f;
    }
    public void set(Vector2f v)
    {
        this.x = v.x;
        this.y = v.y;
    }
    public Vector2f mul(Vector2f v) {
        this.x = x * v.x;
        this.y = y * v.y;
        return this;
    }
    public Vector2f mul(float scalar) {
        this.x = x * scalar;
        this.y = y * scalar;
        return this;
    }
    public Vector2f div(Vector2f v) {
        this.x = x / v.x;
    this.y = y / v.y;
        return this;
    }
    public Vector2f sub(Vector2f v) {
        this.x = x - v.x;
        this.y = y - v.y;
        return this;
    }
    public Vector2f add(Vector2f v) {
        this.x = x + v.x;
        this.y = y + v.y;
        return this;
    }
    public Vector2f add(float x, float y) {
        return add(new Vector2f(x,y));
    }
    public Vector2f sub(float x, float y) {
        return sub(new Vector2f(x,y));
    }

    public Vector2f negate() {
        this.x = -x;
        this.y = -y;
        return this;
    }
    public Vector2f zero() {
        this.x = 0;
        this.y = 0;
        return this;
    }
    public float distance(Vector2f v) {
        float dx = this.x - v.x;
        float dy = this.y - v.y;
        return (float)Math.sqrt(dx * dx + dy * dy);
    }
    public float length() {
        return (float)Math.sqrt(x * x + y * y);
    }
    public float dot(Vector2f v) {
        return x * v.x + y * v.y;
    }

    public Vector2f normalize(float length) {
        float invLength = invsqrt(x * x + y * y) * length;
        this.x = x * invLength;
        this.y = y * invLength;
        return this;
    }
    public static float invsqrt(float r) {
        return 1.0f / (float) java.lang.Math.sqrt(r);
    }

    public Vector2f normalize(float length, Vector2f dest) {
        float invLength = invsqrt(x * x + y * y) * length;
        dest.x = x * invLength;
        dest.y = y * invLength;
        return dest;
    }

    @Override
    public String toString()
    {
         return this.x + "||" + this.y;
    }
}
