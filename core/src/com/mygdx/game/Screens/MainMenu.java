package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.TankStar;
import com.mygdx.game.getSkin;

import java.io.Serializable;

public class MainMenu implements Screen, Serializable {
    final TankStar game;
    private boolean exit;
    private Label exitLabel1, exitLabel2;
    private Skin skin;
    private Stage stage, stage2;
    private Texture textureBackground, textureTank, textureLogo, textureExit;
    private TextButton newGameButton, exitButton, resumeButton, exitButtonPopUp;
    private ImageButton crossButton;
    private OrthographicCamera camera;
    private Sound clickSound;
    public MainMenu(TankStar game){
        this.game = game;
        exit = false;
        stage = new Stage(new ScreenViewport());
        stage2 = new Stage(new ScreenViewport());
        skin = getSkin.get();
        newGameButton = new TextButton("New Game", skin, "default");
        exitButton = new TextButton("Exit", skin, "default");
        resumeButton = new TextButton("Resume", skin, "default");
        exitButtonPopUp = new TextButton("Exit", skin, "default");
        crossButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("CrossButton.png")))));
        newGameButton.setBounds(500, 310, 400, 90);
        resumeButton.setBounds(500, 200, 400, 90);
        exitButton.setBounds(500, 90, 400, 90);
        exitButtonPopUp.setBounds(245, 110, 400, 90);
        crossButton.setBounds(630, 360, 125, 102);
        clickSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/clickSound.wav"));
        stage.addActor(newGameButton);
        stage.addActor(resumeButton);
        stage.addActor(exitButton);
        stage2.addActor(exitButtonPopUp);
        stage2.addActor(crossButton);
        textureBackground = new Texture(Gdx.files.internal("Background1.jpeg"));
        textureTank = new Texture((Gdx.files.internal("Tanks/AbramTank.png")));
        textureLogo = new Texture(Gdx.files.internal("TankStarLogo.png"));
        textureExit = new Texture(Gdx.files.internal("exitPopUp.png"));
        exitLabel1 = new Label("Do you really", skin, "default");
        exitLabel2 = new Label("want to exit ?", skin, "default");
        exitLabel1.setPosition(250, 325);
        exitLabel2.setPosition(250, 250);
        stage2.addActor(exitLabel1);
        stage2.addActor(exitLabel2);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 900, 506);
        Gdx.input.setInputProcessor(stage);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(exit){
            Gdx.input.setInputProcessor(stage2);
        }
        ScreenUtils.clear(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(textureBackground, 0, 0);
        game.batch.draw(textureTank, -15, 82);
        game.batch.draw(textureLogo, 55, 268);
        game.batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        if(exit){
            game.batch.begin();
            game.batch.draw(textureExit, -50, -20);
            game.batch.end();
            stage2.act(Gdx.graphics.getDeltaTime());
            stage2.draw();
        }
        newGameButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clickSound.play();
                game.setScreen(new tankSelectScreen(game));
                dispose();
            }
        });
        crossButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clickSound.play();
                exit = false;
                Gdx.input.setInputProcessor(stage);
            }
        });
        exitButtonPopUp.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clickSound.play();
                Gdx.app.exit();
            }
        });
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clickSound.play();
                exit = true;
            }
        });
        resumeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new resumeScreen(game));
                dispose();
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
