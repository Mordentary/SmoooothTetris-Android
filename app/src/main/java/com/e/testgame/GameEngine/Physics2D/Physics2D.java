package com.e.testgame.GameEngine.Physics2D;

import com.e.testgame.GameEngine.Physics2D.Forces.ForceRegistry;
import com.e.testgame.GameEngine.Physics2D.Forces.Gravity2D;
import com.e.testgame.GameEngine.Physics2D.Rigidbody.Rigidbody2D;
import com.e.testgame.MathClasses.AABB;
import com.e.testgame.MathClasses.OBB;
import com.e.testgame.MathClasses.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class Physics2D {

    private ForceRegistry forceRegistry;
    private List<Rigidbody2D> rigidbodies;
    private Gravity2D gravity;
    private float physicsTime = 0.0f;
    private float physicsTimeStep = 1.0f / 60.0f;
    private float fixedUpdate;



    public Physics2D(float fixedUpdateDt, Vector2f gravity) {
        this.forceRegistry = new ForceRegistry();
        this.rigidbodies = new ArrayList<>();
        this.gravity = new Gravity2D(gravity);
        this.fixedUpdate = fixedUpdateDt;
    }

    /**
     * Updates rigidBodies with deltaTime(It's for non-fixed loop
     * фnd this is not my case, since I blocked the update at 60 frames)
     */
        public void update(float dt) {

        physicsTime += dt;

        if (physicsTime >= 0.0f) {
            physicsTime -= physicsTimeStep;
            forceRegistry.updateForces(dt);

            // Update the velocities of all rigidbodies

            for (int i = 0; i < rigidbodies.size(); i++) {

                rigidbodies.get(i).physicsUpdate(dt);

            }
        }

    }


    /**
     * Updates all rigidBodies.
     */
    public void fixedUpdate() {

        physicsTime += fixedUpdate;

        if (physicsTime >= 0.0f) {
            physicsTime -= physicsTimeStep;
            forceRegistry.updateForces(fixedUpdate);


            // Update the velocities of all rigidbodies

            for (int i = 0; i < rigidbodies.size(); i++) {

                rigidbodies.get(i).physicsUpdate(fixedUpdate);

            }
        }

    }

    public void addRigidbody(Rigidbody2D body) {
        this.rigidbodies.add(body);
        this.forceRegistry.add(body, gravity);
    }

    public Gravity2D getGravity() {
        return gravity;
    }
}
