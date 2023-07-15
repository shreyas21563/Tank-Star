package com.mygdx.game.Trajectory;

import com.badlogic.gdx.math.Vector2;

import java.io.Serializable;

public class getTrajectoryXY implements Serializable {
    public float gravity;
    public Vector2 startVelocity = new Vector2();
    public Vector2 startPoint = new Vector2();

    public float getX(float t, boolean flipped) {
        if(flipped){
            return -startVelocity.x*t - startPoint.x;
        }
        return startVelocity.x * t + startPoint.x;
    }

    public float getY(float t, boolean flipped) {
        return 0.5f * gravity * t * t + startVelocity.y * t + startPoint.y;
    }
}
