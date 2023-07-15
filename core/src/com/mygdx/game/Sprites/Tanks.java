package com.mygdx.game.Sprites;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

import java.io.Serializable;

public class Tanks extends Sprite implements Serializable {
    public World world;
    public Body body;
    private final int playerNum;
    public Tanks(World world, Texture texture, int playerNum, float x, float y){
        super(texture);
        this.world = world;
        this.playerNum = playerNum;
        defineTank(x, y);
        setBounds(0, 0, 100, 53);
        setRegion(texture);
    }
    public void update(float delta){
        setPosition(body.getPosition().x-getWidth()/2, body.getPosition().y-getHeight()/5);
    }
    private void defineTank(float x, float y){
        BodyDef bodyDef = new BodyDef();
        if(x!=-1f && y!=-1f){
            bodyDef.position.set(x, y);
        }
        else if(playerNum==1){
            bodyDef.position.set(100, 250);
        }else{
            bodyDef.position.set(800, 250);
        }
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(4);
        fixtureDef.shape = shape;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0;
        body.createFixture(fixtureDef);
    }

}
