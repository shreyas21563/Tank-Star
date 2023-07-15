package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class getSkin {
    static Skin skin;
    public static Skin get(){
        if(skin==null){
            skin = new Skin(Gdx.files.internal("quantum-horizon/skin/quantum-horizon-ui.json"));
        }
        return skin;
    }
}
