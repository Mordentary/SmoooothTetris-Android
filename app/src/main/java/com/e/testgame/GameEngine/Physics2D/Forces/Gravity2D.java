package com.e.testgame.GameEngine.Physics2D.Forces;

import com.e.testgame.GameEngine.Physics2D.Forces.ForceGenerator;
import com.e.testgame.GameEngine.Physics2D.Rigidbody.Rigidbody2D;
import com.e.testgame.MathClasses.Vector2f;


/**
 *  The only force in my game is gravity. The class inherits the interface and is further treated as an object of the ForceGenerator type.
 */
public class Gravity2D implements ForceGenerator {

    private Vector2f gravity;

    public Gravity2D(Vector2f force) {
        this.gravity = new Vector2f(force);
    }

    @Override
    public void updateForce(Rigidbody2D body, double dt) {
        body.addForce(new Vector2f(gravity).mul(body.getMass()));
    }
}
