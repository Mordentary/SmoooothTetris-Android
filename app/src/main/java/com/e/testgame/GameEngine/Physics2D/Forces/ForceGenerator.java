package com.e.testgame.GameEngine.Physics2D.Forces;

import com.e.testgame.GameEngine.Physics2D.Rigidbody.Rigidbody2D;

/**
 * An interface that uses all forces to update all rigidBody;
 */
public interface ForceGenerator {

    void updateForce(Rigidbody2D body, double dt);
}
