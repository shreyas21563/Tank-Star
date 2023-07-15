package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Save;
import com.mygdx.game.TankStar;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

public class resumeScreen implements Screen, Serializable {
    private final TankStar game;
    private final Stage stage;
    private final Texture backGround;
    private final ImageButton backButton;
    private Skin skin;
    private final TextField textField;
    private final TextButton StartButton;
    private String GameName;
    public resumeScreen(TankStar game){
        this.game = game;
        skin = new Skin(Gdx.files.internal("quantum-horizon/skin/quantum-horizon-ui.json"));
        StartButton = new TextButton("Start", skin, "default");
        StartButton.setPosition(300, 200);
        textField = new TextField("", skin);
        textField.setSize(250, 50);
        textField.setPosition(300, 300);
        backGround = new Texture(Gdx.files.internal("resumeBackground.png"));
        stage = new Stage();
        stage.addActor(textField);
        stage.addActor(StartButton);
        backButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("backButton.png")))));
        backButton.setBounds(0, 415, 65, 66);
        Label resumeLabel = new Label("RESUME", skin, "default");
        resumeLabel.setPosition(350, 450);
        stage.addActor(resumeLabel);
        stage.addActor(backButton);
        Gdx.input.setInputProcessor(stage);
    }
    public void resumeGame(){
        GamesScreen screen = null;
        ObjectInputStream in = null;
        try{
            try {
                in = new ObjectInputStream(Files.newInputStream(Paths.get(GameName + ".txt")));
            } catch (IOException e) {
                System.out.println("Opening problem");
                throw new RuntimeException(e);
            }
            Save save = (Save)in.readObject();
            in.close();
            dispose();
            screen = new GamesScreen(game, save.Num1, save.Num2, save.X1, save.Y1, save.X2, save.Y2);
            screen.restore(save);
            game.setScreen(screen);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    @Override
    public void show() {

    }
    @Override
    public void render(float delta) {
        game.batch.begin();
        game.batch.draw(backGround, 0, 0);
        game.batch.end();
        stage.draw();
        GameName = textField.getText();
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new MainMenu(game));
            }
        });
        StartButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!Objects.equals(GameName, "")){
                    resumeGame();
                }
            }
        });

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
