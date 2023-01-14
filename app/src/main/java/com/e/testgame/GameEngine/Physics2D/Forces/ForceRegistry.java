package com.e.testgame.GameEngine.Physics2D.Forces;

import com.e.testgame.GameEngine.Physics2D.Rigidbody.Rigidbody2D;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores all pairs of rigidBody/force.
 */
public class ForceRegistry {

    private List<ForceRegistration> registry;

    public ForceRegistry() {
        this.registry = new ArrayList<>();
    }

    public void add(Rigidbody2D rb, ForceGenerator fg) {
        ForceRegistration fr = new ForceRegistration(fg, rb);
        registry.add(fr);
    }

    public void remove(Rigidbody2D rb, ForceGenerator fg) {
        ForceRegistration fr = new ForceRegistration(fg, rb);
        registry.remove(fr);
    }

    public void clearList() {
        registry.clear();
    }

    public void updateForces(float dt) {
        for (ForceRegistration _forceReg : registry) {
            _forceReg._forceGen.updateForce(_forceReg._rigidBody, dt);
        }
    }

}