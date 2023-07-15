package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

import javax.swing.*;

public class Balls extends Sprite {
    public World world;
    public Boolean fired;
    public Body body;
    public Tanks tank;
    private Boolean flipped;
    public Balls(World world, Texture texture, Tanks tank, Boolean flipped){
        super(texture);
        this.flipped = flipped;
        this.world = world;
        this.fired = false;
        this.tank = tank;
        defineBalls();
        setBounds(0, 0, 30, 27.9f);
        setRegion(texture);
    }
    public void update(float delta){
        if(!fired){
            setPosition(tank.body.getPosition().x+23, tank.body.getPosition().y+23);
        }else{
                setPosition(body.getPosition().x-15, body.getPosition().y-5);
        }
    }
    private void defineBalls() {
        BodyDef bodyDef = new BodyDef();
        if(flipped){
            bodyDef.position.set(tank.body.getPosition().x-35, tank.body.getPosition().y+25);
        }else{
            bodyDef.position.set(tank.body.getPosition().x+35, tank.body.getPosition().y+25);
        }
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(1);
        fixtureDef.shape = shape;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0;
        body.createFixture(fixtureDef);
    }
}
