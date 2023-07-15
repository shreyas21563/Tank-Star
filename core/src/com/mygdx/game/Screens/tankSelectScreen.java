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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.TankStar;
import com.mygdx.game.getSkin;

import java.io.Serializable;

public class tankSelectScreen implements Screen, Serializable {
    private TankStar game;
    private Sound clickSound;
    private Stage stage1, stage2, stage3;
    private Skin skin;
    private Label hpLabel1;
    private Label nameAbramTank, nameFrostTank, nameTigerTank;
    private Texture textureBackground1, textureBackground2, textureAbramTankBig, textureFrostTankBig, textureTigerTankBig;
    private Texture textureAbramTankSmall, textureFrostTankSmall, textureTigerTankSmall;
    private ImageButton rightButton, leftButton, backButton;
    private int num;
    private Label selectTankPlayer;
    private boolean player1Selected, player2Selected;
    private int tankNumPlayer1, tankNumPlayer2;
    private TextButton selectTankButton, startGameButton;
    private OrthographicCamera camera;

    tankSelectScreen(TankStar game){
        this.game = game;
        clickSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/clickSound.wav"));
        num = 1;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 900, 506);
        player1Selected = false;
        player2Selected = false;
        tankNumPlayer1 = 0;
        tankNumPlayer2 = 0;
        stage1 = new Stage(new ScreenViewport());
        stage2 = new Stage(new ScreenViewport());
        stage3 = new Stage(new ScreenViewport());
        skin = getSkin.get();
        hpLabel1 = new Label("HP 800", skin, "title");
        rightButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("rightside.png")))));
        rightButton.setBounds(850, 225, 50, 38);
        leftButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("leftside.png")))));
        leftButton.setBounds(510, 225, 50, 38);
        backButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("backButton.png")))));
        backButton.setBounds(0, 415, 65, 66);
        nameAbramTank = new Label("ABRAM", skin, "tankName");
        nameFrostTank = new Label("FROST", skin, "tankName");
        nameTigerTank = new Label("TIGER", skin, "tankName");
        selectTankPlayer = new Label("Select Tank Player 1", skin, "title");
        hpLabel1.setPosition(225, 370);
        selectTankPlayer.setPosition(573, 383);
        nameAbramTank.setPosition(165, 425);
        nameFrostTank.setPosition(165, 425);
        nameTigerTank.setPosition(174, 425);
        stage1.addActor(hpLabel1);
        stage1.addActor(nameAbramTank);
        stage1.addActor(rightButton);
        stage1.addActor(selectTankPlayer);
        stage1.addActor(backButton);
        textureBackground1 = new Texture(Gdx.files.internal("backgroundViolet.jpg"));
        textureBackground2 = new Texture(Gdx.files.internal("backgroundBlue.jpg"));
        textureAbramTankBig = new Texture(Gdx.files.internal("Tanks/AbramTank.png"));
        textureFrostTankBig = new Texture(Gdx.files.internal("Tanks/frostTank.png"));
        textureTigerTankBig = new Texture(Gdx.files.internal("Tanks/tigerTank.png"));
        textureAbramTankSmall = new Texture(Gdx.files.internal("Tanks/AbramTankSmall.png"));
        textureFrostTankSmall = new Texture(Gdx.files.internal("Tanks/frostTankSmall.png"));
        textureTigerTankSmall = new Texture(Gdx.files.internal("Tanks/tigerTankSmall.png"));
        selectTankButton = new TextButton("Select Tank", skin, "default");
        selectTankButton.setBounds(510, 60, 400, 90);
        startGameButton = new TextButton("Start Game", skin, "default");
        startGameButton.setBounds(510, 60, 400, 90);
        stage1.addActor(selectTankButton);
        Gdx.input.setInputProcessor(stage1);
    }
    @Override
    public void show() {

    }
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        if(!player2Selected && !player1Selected){
            game.batch.draw(textureBackground1, 0, 0);
        }else{
            game.batch.draw(textureBackground2, 0, 0);
        }
        if(num==1){
            game.batch.draw(textureAbramTankBig, -15, 82);
        }else if(num==2){
            game.batch.draw(textureFrostTankBig, -15, 82);
        }else if(num==3){
            game.batch.draw(textureTigerTankBig, -15, 82);
        }
        game.batch.end();
        if(num==1){
            stage1.draw();
            game.batch.begin();
            game.batch.draw(textureAbramTankSmall, 616, 210);
            game.batch.end();
        }else if(num==2){
            stage2.draw();
            game.batch.begin();
            game.batch.draw(textureFrostTankSmall, 625, 210);
            game.batch.end();
        }else if(num==3){
            stage3.draw();
            game.batch.begin();
            game.batch.draw(textureTigerTankSmall, 632, 210);
            game.batch.end();
        }
        rightButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clickSound.play();
                if(num==1){
                    stage1.dispose();
                    stage2 = new Stage();
                    num++;
                    stage2.addActor(nameFrostTank);
                    stage2.addActor(hpLabel1);
                    if(!player2Selected){
                        stage2.addActor(selectTankButton);
                    }else if(player1Selected && player2Selected){
                        stage2.addActor(startGameButton);
                    }
                    stage2.addActor(rightButton);
                    stage2.addActor(leftButton);
                    stage2.addActor(backButton);
                    stage2.addActor(selectTankPlayer);
                    Gdx.input.setInputProcessor(stage2);
                }else if(num==2){
                    stage2.dispose();
                    stage3 = new Stage();
                    num++;
                    stage3.addActor(nameTigerTank);
                    stage3.addActor(leftButton);
                    stage3.addActor(backButton);
                    if(!player2Selected){
                        stage3.addActor(selectTankButton);
                    }else if(player2Selected && player1Selected){
                        stage3.addActor(startGameButton);
                    }
                    stage3.addActor(hpLabel1);
                    stage3.addActor(selectTankPlayer);
                    Gdx.input.setInputProcessor(stage3);
                }
            }
        });
        leftButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clickSound.play();
                if(num==2){
                    stage2.dispose();
                    num--;
                    stage1 = new Stage();
                    stage1.addActor(nameAbramTank);
                    stage1.addActor(rightButton);
                    stage1.addActor(backButton);
                    if(!player2Selected){
                        stage1.addActor(selectTankButton);
                    }
                    else if(player1Selected && player2Selected){
                        stage1.addActor(startGameButton);
                    }
                    stage1.addActor(hpLabel1);
                    stage1.addActor(selectTankPlayer);
                    Gdx.input.setInputProcessor(stage1);
                }else if(num==3){
                    stage3.dispose();
                    num--;
                    stage2 = new Stage();
                    stage2.addActor(nameFrostTank);
                    stage2.addActor(hpLabel1);
                    stage2.addActor(leftButton);
                    stage2.addActor(backButton);
                    if(!player2Selected){
                        stage2.addActor(selectTankButton);
                    } else if(player1Selected && player2Selected){
                        stage2.addActor(startGameButton);
                    }
                    stage2.addActor(rightButton);
                    stage2.addActor(selectTankPlayer);
                    Gdx.input.setInputProcessor(stage2);
                }
            }
        });
        selectTankButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clickSound.play();
                if((!player1Selected) && (!player2Selected)){
                    tankNumPlayer1 = num;
                    player1Selected = true;
                    selectTankButton.remove();
                    selectTankPlayer = new Label("Select Tank Player 2", skin, "title");
                    selectTankPlayer.setPosition(573, 383);
                    if(num==1){
                        stage1.addActor(selectTankButton);
                        stage1.addActor(selectTankPlayer);
                    }else if(num==2){
                        stage2.addActor(selectTankButton);
                        stage2.addActor(selectTankPlayer);
                    }else if(num==3){
                        stage3.addActor(selectTankButton);
                        stage3.addActor(selectTankPlayer);
                    }
                }else if(player1Selected && !player2Selected){
                    player2Selected = true;
                    tankNumPlayer2 = num;
                    selectTankButton.remove();
                    if(num==1){
                        stage1.addActor(startGameButton);
                    }else if(num==2){
                        stage2.addActor(startGameButton);
                    }else if(num==3){
                        stage3.addActor(startGameButton);
                    }
                }
            }
        });
        startGameButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clickSound.play();
                game.setScreen(new GamesScreen(game, tankNumPlayer1, tankNumPlayer2, -1, -1, -1, -1));
                dispose();
            }
        });
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clickSound.play();
                dispose();
                game.setScreen(new MainMenu(game));
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
        if(num==1){
            stage1.dispose();
        }
        if(num==2){
            stage2.dispose();
        }
        if(num==3){
            stage3.dispose();
        }
    }
}