package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.MainMenu;

import java.io.Serializable;

public class TankStar extends Game implements Serializable{
    public SpriteBatch batch;
    public static AssetManager manager;
    @Override
    public void create() {
        batch = new SpriteBatch();
        manager = new AssetManager();
        manager.load("Sounds/hello.mp3", Music.class);
        manager.finishLoading();
        setScreen(new MainMenu(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
//        manager.dispose();
        batch.dispose();
    }
}
