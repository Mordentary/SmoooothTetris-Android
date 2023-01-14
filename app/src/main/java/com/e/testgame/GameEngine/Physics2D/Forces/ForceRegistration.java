package com.e.testgame.GameEngine.Physics2D.Forces;

import com.e.testgame.GameEngine.Physics2D.Rigidbody.Rigidbody2D;


/**
 *Stores which force and which rigidBody is being affected.
 */
public class ForceRegistration {
    public ForceGenerator _forceGen;
    public Rigidbody2D _rigidBody;


    public ForceRegistration(ForceGenerator _forceGen, Rigidbody2D _rigidBody) {
        this._forceGen = _forceGen;
        this._rigidBody = _rigidBody;
    }

}