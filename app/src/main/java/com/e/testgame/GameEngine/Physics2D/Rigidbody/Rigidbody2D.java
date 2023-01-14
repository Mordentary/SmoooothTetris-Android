package com.e.testgame.GameEngine.Physics2D.Rigidbody;

import com.e.testgame.MathClasses.Vector2f;

public class Rigidbody2D  {

    private Vector2f resultVelocity = new Vector2f();
    private float _rotation = 0.0f;
    private float mass = 0.0f;
    private float inverseMass = 0.0f;
    private Vector2f forceAccum = new Vector2f();
    private Vector2f linearVelocity = new Vector2f();
    public Vector2f getResultVelocity() {
        return resultVelocity;
    }

    /**
     Mixes all forces, taking into account mass, acceleration, etc. In order to achieve the natural behavior of the object. */
    public void physicsUpdate(float dt) {
        if (this.mass == 0.0f) return;

        // Calculate linear velocity
        Vector2f acceleration = new Vector2f(forceAccum).mul(this.inverseMass);
        linearVelocity.add(acceleration.mul(dt));

        // Update the linear position
        this.resultVelocity.add(new Vector2f(linearVelocity).mul(dt));

        clearAccumulators();
    }

    public void clearAccumulators() {
        this.forceAccum.zero();
    }

    public void setTransform(Vector2f position, float rotation) {
        this.resultVelocity.set(position);
        this._rotation = rotation;
    }

    public void setTransform(Vector2f position) {
        this.resultVelocity.set(position);
    }

    public float getRotation() {
        return _rotation;
    }

    public float getMass() {
        return mass;
    }


    public void zeroForcesOnSide(String side)
    {
        // left, right, top, bottom


        if(side == "bottom")
        {
            this.resultVelocity.y -= this.resultVelocity.y;
        }
        if(side == "right")
        {
            this.resultVelocity.x -= this.resultVelocity.x;
        }
        if(side == "left")
        {
            this.resultVelocity.x -= this.resultVelocity.x;
        }


    }

    public void setMass(float mass) {
        this.mass = mass;
        if (this.mass != 0.0f) {
            this.inverseMass = 1.0f / this.mass;
        }
    }
/** Adds all forces to one accumulator vector */
    public void addForce(Vector2f force) {
        this.forceAccum.add(force);
    }



}
