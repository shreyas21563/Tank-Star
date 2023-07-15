package com.mygdx.game.Trajectory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.io.Serializable;

public class getTrajectory extends Actor implements Serializable {
    public boolean flipped;
    public float power = 40f;
    public float angle = 20f;
    public getTrajectoryXY TrajectoryXY;

    public int trajectoryPointCount = 10;
    public float timeSeparation = 0.2f;

    public getTrajectory(float gravity) {
        this.TrajectoryXY = new getTrajectoryXY();
        this.TrajectoryXY.gravity = gravity;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        TrajectoryXY.startVelocity.set(power, 0f);
        TrajectoryXY.startVelocity.rotateDeg(angle);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float t = 0f;
        float width = this.getWidth();
        float height = this.getHeight();
        float timeSeparation = this.timeSeparation;
        for (int i = 0; i<trajectoryPointCount; i++) {
            float x = this.getX() + TrajectoryXY.getX(t, flipped);
            float y = this.getY() + TrajectoryXY.getY(t, flipped);
            batch.setColor(this.getColor());
            batch.draw(new Texture(Gdx.files.internal("dot.png")), x, y, width, height);
            t += timeSeparation;
        }
    }

    @Override
    public Actor hit(float x, float y, boolean touchable) {return null;}
}
