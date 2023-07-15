package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.io.Serializable;
import java.util.ArrayList;

public class Factory implements Serializable {
    public static ArrayList<ImageButton> getMissiles(int number){
        ArrayList<ImageButton> array = new ArrayList<ImageButton>();
        switch (number){
            case 1:
                array.add(new ImageButton(new TextureRegionDrawable(new Texture("Balls/Abrams/Abrams1.png"))));
                array.add(new ImageButton(new TextureRegionDrawable(new Texture("Balls/Abrams/Abrams2.png"))));
                array.add(new ImageButton(new TextureRegionDrawable(new Texture("Balls/Abrams/Abrams3.png"))));
            case 2:
                array.add(new ImageButton(new TextureRegionDrawable(new Texture("Balls/Frost/Frost1.png"))));
                array.add(new ImageButton(new TextureRegionDrawable(new Texture("Balls/Frost/Frost2.png"))));
                array.add(new ImageButton(new TextureRegionDrawable(new Texture("Balls/Frost/Frost3.png"))));
            case 3:
                array.add(new ImageButton(new TextureRegionDrawable(new Texture("Balls/Tiger/Tiger1.png"))));
                array.add(new ImageButton(new TextureRegionDrawable(new Texture("Balls/Tiger/Tiger2.png"))));
                array.add(new ImageButton(new TextureRegionDrawable(new Texture("Balls/Tiger/Tiger3.png"))));
        }
        return array;
    }
    public static ArrayList<Label> getLabels(int number){
        ArrayList<Label> array = new ArrayList<>();
        Skin skin = getSkin.get();
        switch(number){
            case 1:
                array.add(new Label("Scorcher", skin, "title"));
                array.add(new Label("Jupiter", skin, "title"));
                array.add(new Label("Thermite", skin, "title"));
            case 2:
                array.add(new Label("Dark Moon", skin, "title"));
                array.add(new Label("Frost Blast", skin, "title"));
                array.add(new Label("Snow Ball", skin, "title"));
            case 3:
                array.add(new Label("Plasma", skin, "title"));
                array.add(new Label("Black Hole", skin, "title"));
                array.add(new Label("DeadEye", skin, "title"));
        }
        return array;
    }
    public static Texture getTexture(int num){
        switch (num){
            case 1:
                return new Texture(Gdx.files.internal("Tanks/AbramTankSmallSprite.png"));
            case 2:
                return new Texture(Gdx.files.internal("Tanks/frostTankSmallSprite.png"));
            case 3:
                return new Texture(Gdx.files.internal("Tanks/tigerTankSmallSprite.png"));
            case 4:
                return new Texture(Gdx.files.internal("Tanks/AbramTankSmallSpriteFlipped.png"));
            case 5:
                return new Texture(Gdx.files.internal("Tanks/frostTankSmallSpriteFlipped.png"));
            case 6:
                return new Texture(Gdx.files.internal("Tanks/tigerTankSmallSpriteFlipped.png"));
        }
        return null;
    }
    public static Texture getTexture(int tankNum, int ballNum){
        switch(tankNum){
            case 1:
                switch (ballNum){
                    case 1:
                        return new Texture("Balls/Abrams/Abrams1.png");
                    case 2:
                        return new Texture("Balls/Abrams/Abrams2.png");
                    case 3:
                        return new Texture("Balls/Abrams/Abrams3.png");
                }
            case 2:
                switch (ballNum){
                    case 1:
                        return new Texture("Balls/Frost/Frost1.png");
                    case 2:
                        return new Texture("Balls/Frost/Frost2.png");
                    case 3:
                        return new Texture("Balls/Frost/Frost3.png");
                }
            case 3:
                switch (ballNum){
                    case 1:
                        return new Texture("Balls/Tiger/Tiger1.png");
                    case 2:
                        return new Texture("Balls/Tiger/Tiger2.png");
                    case 3:
                        return new Texture("Balls/Tiger/Tiger3.png");
                }
        }
        return null;
    }
}
