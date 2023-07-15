package com.mygdx.game.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.*;
import com.mygdx.game.ProgressBars.fuelBar;
import com.mygdx.game.ProgressBars.healthBar;
import com.mygdx.game.Sprites.Balls;
import com.mygdx.game.Sprites.Tanks;
import com.mygdx.game.Trajectory.getTrajectory;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
public class GamesScreen extends ApplicationAdapter implements Screen, Serializable {
    private Music music;
    String GameName;
    private TankStar game;
    private TextField textField;
    private Boolean chooseMissile;
    private fuelBar FuelBar1, FuelBar2;
    private healthBar HealthBar1, HealthBar2;
    private OrthographicCamera camera;
    private Skin skin;
    private Boolean Player1BallFired = false, Player2BallFired = false;
    private Viewport gamePort;
    private OrthogonalTiledMapRenderer renderer;
    private final BitmapFont font;
    private Label pauseLabel, Player1Label, Player2Label, gameOverLabel;
    private Texture  pauseMenu;
    private ImageButton pauseButton;
    private TextButton resumeButton, saveButton, RestartButton, exitButton;
    private ImageButton chooseMissileLeft, chooseMissileRight;
    private Label chooseMissileLabel;
    private int MissileNumPlayer1, MissileNumPlayer2;
    private boolean paused;
    private Balls ballPlayer1, ballPlayer2;
    private boolean turnPlayer1;
    private final getTrajectory actor1, actor2;
    private final ArrayList<ImageButton> MissilesPlayer1, MissilesPlayer2;
    private Stage stage1;
    private  Stage stage2;
    private  Stage mainStage;
    private  Stage chooseMissilePlayer1Stage;
    private  Stage chooseMissilePlayer2Stage;
    private Stage pauseStage;
    private  Stage GameOverStage;
    private Texture texturePlayer1 = null, texturePlayer2 = null;
    private Texture texturePlayer1Flipped = null;
    private Texture texturePlayer2Flipped = null;
    private final Texture purple;
    private final World world;
    private final Box2DDebugRenderer box2DDebugRenderer;
    private final Tanks tankPlayer1;
    private final Tanks tankPlayer2;
    private final int Player1TankNum, Player2TankNum;
    private TiledMap map;
    private final Sound collision, fire;
    public void restore(Save save){
        this.FuelBar1.setValue(save.FuelBar1Value);
        this.FuelBar2.setValue(save.FuelBar2Value);
        this.HealthBar1.setValue(save.HealthBar1Value);
        this.HealthBar2.setValue(save.HealthBar2Value);
        this.turnPlayer1 = save.turnPlayer1;
        this.tankPlayer1.setPosition(save.X1, save.Y1);
        this.tankPlayer1.setRotation(save.Angle1);
        this.tankPlayer2.setPosition(save.X2, save.Y2);
        this.tankPlayer2.setRotation(save.Angle2);
    }
    private void initialStages(){
        GameOverStage = new Stage();
        pauseStage = new Stage();
        stage1 = new Stage();
        stage2 = new Stage();
        mainStage = new Stage();
        chooseMissilePlayer1Stage = new Stage();
        chooseMissilePlayer2Stage = new Stage();
    }
    private void setFuelBar(){
        FuelBar1 = new fuelBar(140, 44);
        FuelBar2= new fuelBar(140, 44);
        FuelBar1.setPosition(25, 150);
        FuelBar2.setPosition(725, 150);
        FuelBar1.setValue(1f);
        FuelBar2.setValue(1f);
        stage1.addActor(FuelBar1);
        stage2.addActor(FuelBar2);
    }
    private void setHealthBar(){
        HealthBar1 = new healthBar(180, 35);
        HealthBar2 = new healthBar(180, 35);
        HealthBar1.setPosition(150, 455);
        HealthBar2.setPosition(555, 455);
        mainStage.addActor(HealthBar1);
        mainStage.addActor(HealthBar2);
    }
    private void setLabels(){
        Player1Label = new Label("Player 1", skin, "title");
        Player2Label = new Label("Player 2", skin, "title");
        Player1Label.setPosition(185, 465);
        Player2Label.setPosition(590, 465);
        mainStage.addActor(Player1Label);
        mainStage.addActor(Player2Label);
    }
    private void setMissilesPlayer1(){

        MissilesPlayer1.get(0).setPosition(605, 350);
        chooseMissilePlayer1Stage.addActor(MissilesPlayer1.get(0));
        MissilesPlayer1.get(1).setPosition(605, 200);
        chooseMissilePlayer1Stage.addActor(MissilesPlayer1.get(1));
        MissilesPlayer1.get(2).setPosition(605, 50);
        chooseMissilePlayer1Stage.addActor(MissilesPlayer1.get(2));
    }
    private void setMissileLabelPlayer1(){
        ArrayList<Label> missileLabelPlayer1 = Factory.getLabels(Player1TankNum);
        missileLabelPlayer1.get(0).setPosition(705, 400);
        chooseMissilePlayer1Stage.addActor(missileLabelPlayer1.get(0));
        missileLabelPlayer1.get(1).setPosition(705, 250);
        chooseMissilePlayer1Stage.addActor(missileLabelPlayer1.get(1));
        missileLabelPlayer1.get(2).setPosition(705, 100);
        chooseMissilePlayer1Stage.addActor(missileLabelPlayer1.get(2));
    }
    private void setMissilesPlayer2(){
        MissilesPlayer2.get(0).setPosition(605, 350);
        chooseMissilePlayer2Stage.addActor(MissilesPlayer2.get(0));
        MissilesPlayer2.get(1).setPosition(605, 200);
        chooseMissilePlayer2Stage.addActor(MissilesPlayer2.get(1));
        MissilesPlayer2.get(2).setPosition(605, 50);
        chooseMissilePlayer2Stage.addActor(MissilesPlayer2.get(2));
    }
    private void setMissileLabelPlayer2(){
        ArrayList<Label> missileLabelPlayer2 = Factory.getLabels(Player2TankNum);
        missileLabelPlayer2.get(0).setPosition(705, 400);
        chooseMissilePlayer2Stage.addActor(missileLabelPlayer2.get(0));
        missileLabelPlayer2.get(1).setPosition(705, 250);
        chooseMissilePlayer2Stage.addActor(missileLabelPlayer2.get(1));
        missileLabelPlayer2.get(2).setPosition(705, 100);
        chooseMissilePlayer2Stage.addActor(missileLabelPlayer2.get(2));
    }
    private void setTrajectory(getTrajectory actor, boolean bool, Stage stage){
        actor.setHeight(10f);
        actor.setWidth(10f);
        actor.flipped = bool;
        stage.addActor(actor);
    }
    private void setChooseLabel(){
        chooseMissileLabel = new Label("Choose a Ball", skin, "title");
        chooseMissileLabel.setPosition(660, 480);
        chooseMissilePlayer1Stage.addActor(chooseMissileLabel);
    }
    private void loadMap(){
        camera = new OrthographicCamera();
        gamePort = new FitViewport(900, 506, camera);
        TmxMapLoader mapLoader = new TmxMapLoader();
        map = mapLoader.load("new Tiled/Graveyard Layout.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        camera.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2, 0);
    }
    private void setChooseMissileButtons(){
        chooseMissileLeft = new ImageButton(new TextureRegionDrawable(new Texture("chooseMissileLeft.png")));
        chooseMissileLeft.setPosition(850, 20);
        chooseMissileRight = new ImageButton(new TextureRegionDrawable(new Texture("chooseMissileRight.png")));
        chooseMissileRight.setPosition(550, 20);
        mainStage.addActor(chooseMissileLeft);
        chooseMissilePlayer1Stage.addActor(chooseMissileRight);
    }
    public GamesScreen(TankStar game, int player1TankNum, int player2TankNum, float x1, float y1, float x2, float y2){
        initialStages();
        collision = Gdx.audio.newSound(Gdx.files.internal("Sounds/collision.mp3"));
        fire = Gdx.audio.newSound(Gdx.files.internal("Sounds/fire.mp3"));
        turnPlayer1 = true;
        this.Player1TankNum = player1TankNum;
        this.Player2TankNum = player2TankNum;
        MissileNumPlayer1 = 1;
        MissileNumPlayer2 = 1;

        skin = getSkin.get();

        music = TankStar.manager.get("Sounds/hello.mp3", Music.class);
        music.setLooping(true);
        music.setVolume(50f);
        music.play();
        chooseMissile = false;
        font = new BitmapFont();

        MissilesPlayer1 = Factory.getMissiles(player1TankNum);
        MissilesPlayer2 = Factory.getMissiles(player2TankNum);

        this.game = game;

        setFuelBar();
        setHealthBar();
        setLabels();
        setMissilesPlayer1();
        setMissileLabelPlayer1();
        setMissilesPlayer2();
        setMissileLabelPlayer2();


        actor1 = new getTrajectory(-10);
        actor2 = new getTrajectory(-10);
        setTrajectory(actor1, false, stage1);
        setTrajectory(actor2, true, stage2);

        Gdx.input.setInputProcessor(mainStage);

        paused = false;

        setChooseLabel();
        loadMap();
        setChooseMissileButtons();

        purple = new Texture("purple.jpg");
        pauseMenu = new Texture(Gdx.files.internal("pauseMenu.png"));
        pauseLabel = new Label("PAUSED", skin, "default");
        gameOverLabel = new Label("Game Over", skin, "default");
        gameOverLabel.setPosition(335, 385);
        GameOverStage.addActor(gameOverLabel);
        RestartButton = new TextButton("Restart", skin, "default");
        RestartButton.setBounds(335, 275, 275, 60);
        exitButton = new TextButton("Exit", skin, "default");
        exitButton.setBounds(335, 180, 275, 60);
        GameOverStage.addActor(RestartButton);
        GameOverStage.addActor(exitButton);
        pauseLabel.setPosition(365, 385);
        pauseButton = new ImageButton(new TextureRegionDrawable(new Texture("pauseButton.png")));
        pauseButton.setPosition(-10, 420);
        resumeButton = new TextButton("Resume", skin, "default");
        resumeButton.setBounds(335, 275, 275, 60);
        saveButton = new TextButton("Save", skin, "default");
        saveButton.setBounds(335, 180, 275, 60);
        mainStage.addActor(pauseButton);
        textField = new TextField("", getSkin.get());
        textField.setSize(250, 50);
        textField.setPosition(350, 450);
        pauseStage.addActor(textField);
        pauseStage.addActor(pauseLabel);
        pauseStage.addActor(resumeButton);
        pauseStage.addActor(saveButton);
        world = new World(new Vector2(0, -50f), true);
        box2DDebugRenderer = new Box2DDebugRenderer();

        texturePlayer1 = Factory.getTexture(player1TankNum);
        texturePlayer1Flipped = Factory.getTexture(3 + player1TankNum);

        texturePlayer2 = Factory.getTexture(player2TankNum);
        texturePlayer2Flipped = Factory.getTexture(3 + player2TankNum);
        tankPlayer1 = new Tanks(world, texturePlayer1, 1, x1, y1);
        tankPlayer2 = new Tanks(world, texturePlayer2Flipped, 2, x2, y2);

        BodyDef bodyDef = new BodyDef();
        PolygonShape polygonShape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;
        for(RectangleMapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = object.getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rectangle.getX()+rectangle.getWidth()/2), (rectangle.getY() + rectangle.getHeight()/2));
            body = world.createBody(bodyDef);
            polygonShape.setAsBox(rectangle.getWidth()/2, rectangle.getHeight()/2);
            fixtureDef.shape = polygonShape;
            body.createFixture(fixtureDef);
        }
    }
    private void handleFiring(float delta){
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && !Player1BallFired && !Player2BallFired && !paused){
            music.setVolume(20f);
            fire.play();
            music.setVolume(50f);
            if(turnPlayer1){
                ballPlayer1 = new Balls(world, Factory.getTexture(Player1TankNum, MissileNumPlayer1), tankPlayer1, tankPlayer1.getTexture()==texturePlayer1Flipped);
                ballPlayer1.fired = true;
                Player1BallFired = true;
                if(tankPlayer1.getTexture()==texturePlayer1){
                    ballPlayer1.body.applyLinearImpulse(new Vector2(actor1.power*actor1.TrajectoryXY.startVelocity.x/4, actor1.power*actor1.TrajectoryXY.startVelocity.y/3), ballPlayer1.body.getWorldCenter(), true);
                }else if(tankPlayer1.getTexture()==texturePlayer1Flipped){
                    ballPlayer1.body.applyLinearImpulse(new Vector2(-actor1.power*actor1.TrajectoryXY.startVelocity.x/4, actor1.power*actor1.TrajectoryXY.startVelocity.y/3), ballPlayer1.body.getWorldCenter(), true);
                }
            }else{
                ballPlayer2 = new Balls(world, Factory.getTexture(Player2TankNum, MissileNumPlayer2), tankPlayer2, tankPlayer2.getTexture()==texturePlayer2Flipped);
                ballPlayer2.fired = true;
                Player2BallFired = true;
                if(tankPlayer2.getTexture()==texturePlayer2){
                    ballPlayer2.body.applyLinearImpulse(new Vector2(actor2.power*actor2.TrajectoryXY.startVelocity.x/4, actor2.power*actor2.TrajectoryXY.startVelocity.y/3), ballPlayer2.body.getWorldCenter(), true);
                }else if(tankPlayer2.getTexture()==texturePlayer2Flipped){
                    ballPlayer2.body.applyLinearImpulse(new Vector2(-actor2.power*actor2.TrajectoryXY.startVelocity.x/4, actor2.power*actor2.TrajectoryXY.startVelocity.y/3), ballPlayer2.body.getWorldCenter(), true);
                }
            }
            turnPlayer1 = (!turnPlayer1);
            if(turnPlayer1){
                MissileNumPlayer2 = 1;
                FuelBar1.setValue(1f);
                chooseMissilePlayer1Stage.addActor(chooseMissileLabel);
                chooseMissilePlayer1Stage.addActor(chooseMissileRight);
            }else{
                MissileNumPlayer1 = 1;
                FuelBar2.setValue(1f);
                chooseMissilePlayer2Stage.addActor(chooseMissileLabel);
                chooseMissilePlayer2Stage.addActor(chooseMissileRight);
            }
        }
    }
    private void updateTankPlayer1(float delta){
        if(Gdx.input.isKeyPressed(Input.Keys.D) && tankPlayer1.body.getLinearVelocity().x<=30 && turnPlayer1 && !Player2BallFired && !paused){
            if(tankPlayer1.body.getLinearVelocity().y>0 && tankPlayer1.getRotation()<=0){
                tankPlayer1.rotate(10f);
            }else if(tankPlayer1.body.getLinearVelocity().y<0 && tankPlayer1.getRotation()>=0){
                tankPlayer1.rotate(-10f);
            }else if(tankPlayer1.body.getLinearVelocity().y==0 && tankPlayer1.getRotation()!=0){
                tankPlayer1.rotate(-tankPlayer1.getRotation());
            }
            if(FuelBar1.getValue()>0){
                tankPlayer1.body.applyLinearImpulse(new Vector2(15f, 0), tankPlayer1.body.getWorldCenter(), true);
            }
            FuelBar1.setValue(FuelBar1.getValue() - 0.03f);
            tankPlayer1.setRegion(texturePlayer1);
        }if(Gdx.input.isKeyPressed(Input.Keys.A) && tankPlayer1.body.getLinearVelocity().x>=-30 && turnPlayer1 && !Player2BallFired && !paused){
            if(tankPlayer1.body.getLinearVelocity().y>0 && tankPlayer1.getRotation()>=0){
                tankPlayer1.rotate(-10f);
            }else if(tankPlayer1.body.getLinearVelocity().y<0 && tankPlayer1.getRotation()<=0){
                tankPlayer1.rotate(10f);
            }else if(tankPlayer1.body.getLinearVelocity().y==0 && tankPlayer1.getRotation()!=0){
                tankPlayer1.rotate(-tankPlayer1.getRotation());
            }
            if(FuelBar1.getValue()>0){
                tankPlayer1.body.applyLinearImpulse(new Vector2(-15f, 0), tankPlayer1.body.getWorldCenter(), true);
            }
            FuelBar1.setValue(FuelBar1.getValue() - 0.03f);
            tankPlayer1.setRegion(texturePlayer1Flipped);
        }
    }
    private void updateTankPlayer2(float delta){
        if(Gdx.input.isKeyPressed(Input.Keys.D) && tankPlayer2.body.getLinearVelocity().x<=30 && !turnPlayer1 && !Player1BallFired && !paused){
            if(tankPlayer2.body.getLinearVelocity().y>0 && tankPlayer2.getRotation()<=0){
                tankPlayer2.rotate(10f);
            }else if(tankPlayer2.body.getLinearVelocity().y<0 && tankPlayer2.getRotation()>=0){
                tankPlayer2.rotate(-10f);
            }else if(tankPlayer2.body.getLinearVelocity().y==0 && tankPlayer2.getRotation()!=0){
                tankPlayer2.rotate(-tankPlayer2.getRotation());
            }
            if(FuelBar2.getValue()>0){
                tankPlayer2.body.applyLinearImpulse(new Vector2(15f, 0), tankPlayer2.body.getWorldCenter(), true);
            }
            FuelBar2.setValue(FuelBar2.getValue() - 0.03f);
            tankPlayer2.setRegion(texturePlayer2);
        }if(Gdx.input.isKeyPressed(Input.Keys.A) && tankPlayer2.body.getLinearVelocity().x>=-30 && !turnPlayer1 && !Player1BallFired && !paused){
            if(tankPlayer2.body.getLinearVelocity().y>0 && tankPlayer2.getRotation()>=0){
                tankPlayer2.rotate(-10f);
            }else if(tankPlayer2.body.getLinearVelocity().y<0 && tankPlayer2.getRotation()<=0){
                tankPlayer2.rotate(10f);
            }else if(tankPlayer2.body.getLinearVelocity().y==0 && tankPlayer2.getRotation()!=0){
                tankPlayer2.rotate(-tankPlayer2.getRotation());
            }
            if(FuelBar2.getValue()>0){
                tankPlayer2.body.applyLinearImpulse(new Vector2(-15f, 0), tankPlayer2.body.getWorldCenter(), true);
            }
            FuelBar2.setValue(FuelBar2.getValue() - 0.03f);
            tankPlayer2.setRegion(texturePlayer2Flipped);
        }
    }
    private void handleTrajectory(float delta){
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Player1BallFired && !Player2BallFired && !paused){
            if(turnPlayer1 && actor1.power>=40){
                actor1.power-=0.5;
            }else if(actor2.power<=60){
                actor2.power+=0.5;
            }
        }if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !Player1BallFired && !Player2BallFired && !paused){
            if(turnPlayer1 && actor1.power<=60){
                actor1.power+=0.5;
            }else if(actor2.power>=40){
                actor2.power-=0.5;
            }
        }if(Gdx.input.isKeyPressed(Input.Keys.UP) && !Player1BallFired && !Player2BallFired && !paused){
            if(turnPlayer1){
                actor1.angle+=0.5;
            }else{
                actor2.angle+=0.5;
            }
        }if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && !Player1BallFired && !Player2BallFired && !paused){
            if(turnPlayer1){
                actor1.angle-=0.5;
            }else{
                actor2.angle-=0.5;
            }
        }
    }
    private void updateTankBall(float delta){
        tankPlayer1.update(delta);
        tankPlayer2.update(delta);
        if(Player1BallFired){
            ballPlayer1.update(delta);
        }if(Player2BallFired){
            ballPlayer2.update(delta);
        }
    }
    private void update(float delta) {
        handleFiring(delta);
        updateTankPlayer1(delta);
        updateTankPlayer2(delta);
        handleTrajectory(delta);
        updateTankBall(delta);
        world.step(1/60f, 6, 2);
        camera.update();
        renderer.setView(camera);
    }
    private void drawTankBall(float delta){
        game.batch.begin();
        tankPlayer1.draw(game.batch);
        tankPlayer2.draw(game.batch);
        if(Player1BallFired){
            if(ballPlayer1.body.getLinearVelocity().y!=0){
                ballPlayer1.draw(game.batch);
            }else {
                Player1BallFired = false;
                world.destroyBody(ballPlayer1.body);
                ballPlayer1 = null;
            }
        }
        if(Player2BallFired){
            if(ballPlayer2.body.getLinearVelocity().y!=0){
                ballPlayer2.draw(game.batch);
            }else{
                Player2BallFired = false;
                world.destroyBody(ballPlayer2.body);
                ballPlayer2 = null;
            }
        }
        game.batch.end();
    }
    @Override
    public void show() {

    }
    private void missileInputPlayer1(){
        MissilesPlayer1.get(0).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MissileNumPlayer1 = 1;
            }
        });
        MissilesPlayer1.get(1).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MissileNumPlayer1 = 2;
            }
        });
        MissilesPlayer1.get(2).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MissileNumPlayer1 = 3;
            }
        });
    }
    private void missileInputPlayer2(){
        MissilesPlayer2.get(0).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MissileNumPlayer2 = 1;
            }
        });
        MissilesPlayer2.get(1).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MissileNumPlayer2 = 2;
            }
        });
        MissilesPlayer2.get(2).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MissileNumPlayer2 = 3;
            }
        });
    }
    @Override
    public void render(float delta) {
        if(HealthBar1.getValue()==0 || HealthBar2.getValue()==0){
            paused = true;
            pauseStage = GameOverStage;
        }
        if(paused) {
            Gdx.input.setInputProcessor(pauseStage);
        }
        update(delta);
        if(ballPlayer1!=null && ballPlayer1.fired){
            if(ballPlayer1.body.getPosition().x>tankPlayer2.body.getPosition().x-20 && ballPlayer1.body.getPosition().x<tankPlayer2.body.getPosition().x+20 && ballPlayer1.body.getPosition().y<tankPlayer2.body.getPosition().y+20){
                HealthBar2.setValue(HealthBar2.getValue()-0.2f);
                world.destroyBody(ballPlayer1.body);
                ballPlayer1 = null;
                Player1BallFired = false;
                music.setVolume(20f);
                collision.play();
                tankPlayer2.body.applyLinearImpulse(new Vector2(15f, 0), tankPlayer2.body.getWorldCenter(), true);
                music.setVolume(50f);
            }
        }
        if(ballPlayer2!=null && ballPlayer2.fired){
            if(ballPlayer2.body.getPosition().x>tankPlayer1.body.getPosition().x-20 && ballPlayer2.body.getPosition().x<tankPlayer1.body.getPosition().x+20 && ballPlayer2.body.getPosition().y<tankPlayer1.body.getPosition().y+20){
                HealthBar1.setValue(HealthBar1.getValue()-0.2f);
                world.destroyBody(ballPlayer2.body);
                ballPlayer2 = null;
                Player2BallFired = false;
                music.setVolume(20f);
                collision.play();
                tankPlayer1.body.applyLinearImpulse(new Vector2(-15f, 0), tankPlayer1.body.getWorldCenter(), true);
                music.setVolume(50f);
            }
        }
        music.play();
        missileInputPlayer1();
        missileInputPlayer2();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
        box2DDebugRenderer.render(world, camera.combined);
        game.batch.setProjectionMatrix(camera.combined);
        drawTankBall(delta);
        if(turnPlayer1){
            if(tankPlayer1.getTexture()==texturePlayer1){
                actor1.setX(tankPlayer1.getX()+83);
                actor1.setY(tankPlayer1.getY()+26);
                actor1.flipped = false;
            }else if(tankPlayer1.getTexture()==texturePlayer1Flipped){
                actor1.setX(tankPlayer1.getX());
                actor1.setY(tankPlayer1.getY()+29);
                actor1.flipped = true;
            }
        }else{
            if(tankPlayer2.getTexture()==texturePlayer2){
                    actor2.setX(tankPlayer2.getX()+83);
                    actor2.setY(tankPlayer2.getY()+26);
                actor2.flipped = false;
            }else if(tankPlayer2.getTexture()==texturePlayer2Flipped){
                actor2.setX(tankPlayer2.getX());
                actor2.setY(tankPlayer2.getY()+29);
                actor2.flipped = true;
            }
        }
        if(turnPlayer1){
            stage1.act(delta);
            stage1.draw();
        }else{
            stage2.act(delta);
            stage1.act();
            stage2.draw();
        }
        mainStage.draw();
        mainStage.act();
        game.batch.begin();
        font.getData().setScale(1.2f);
        if(turnPlayer1 && FuelBar1.getValue()>0){
            font.setColor(Color.WHITE);
            font.draw(game.batch, "FUEL", 75, 178);
        } else if(!turnPlayer1 && FuelBar2.getValue()>0) {
            font.setColor(Color.WHITE);
            font.draw(game.batch, "FUEL", 775, 178);
        }else if(turnPlayer1){
            font.setColor(Color.RED);
            font.draw(game.batch, "FUEL EMPTY", 50, 178);
        }else{
            font.setColor(Color.RED);
            font.draw(game.batch, "FUEL EMPTY", 750, 178);
        }
        if(chooseMissile){
            game.batch.draw(purple, 600, 0, 300, 550);
        }
        game.batch.end();
        if(chooseMissile && turnPlayer1){
            chooseMissilePlayer1Stage.draw();
        }else if(chooseMissile){
            chooseMissilePlayer2Stage.draw();
        }
        chooseMissileLeft.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                chooseMissile = true;
                if(turnPlayer1){
                    Gdx.input.setInputProcessor(chooseMissilePlayer1Stage);
                }else{
                    Gdx.input.setInputProcessor(chooseMissilePlayer2Stage);
                }
            }
        });
        chooseMissileRight.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                chooseMissile = false;
                Gdx.input.setInputProcessor(mainStage);
            }
        });
        if(paused){
            game.batch.begin();
            game.batch.draw(pauseMenu, 275, 0);
            game.batch.end();
            pauseStage.draw();
            GameName = textField.getText();
        }
        exitRestart(delta);
        pauser(delta);
        resumer(delta);
        saver(delta);
    }
    private void exitRestart(float delta){
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                music.stop();
                dispose();
                game.setScreen(new MainMenu(game));
            }
        });
        RestartButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new GamesScreen(game, Player1TankNum, Player2TankNum, -1, -1, -1, -1));
            }
        });
    }
    private void pauser(float delta){
        pauseButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                paused = true;
            }
        });
    }
    private void resumer(float delta){
        resumeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                paused = false;
                Gdx.input.setInputProcessor(mainStage);
            }
        });
    }
    private void saver(float delta){
        saveButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!Objects.equals(GameName, "")){
                    ObjectOutputStream out = null;
                    try{
                        try {
                            out = new ObjectOutputStream(Files.newOutputStream(Paths.get(GameName + ".txt")));
                        } catch (IOException e) {
                            System.out.println("Opening problem");
                            throw new RuntimeException(e);
                        }
                        out.writeObject(new Save(FuelBar1.getValue(), FuelBar2.getValue(), HealthBar1.getValue(), HealthBar2.getValue(), turnPlayer1, tankPlayer1.body.getPosition().x, tankPlayer1.body.getPosition().y, tankPlayer1.getRotation(), tankPlayer2.body.getPosition().x, tankPlayer2.body.getPosition().y, tankPlayer2.getRotation(), Player1TankNum, Player2TankNum));
                        out.close();
                        dispose();
                        music.stop();
                        game.setScreen(new MainMenu(game));
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }}
        });
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
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
        stage1.dispose();
        stage2.dispose();
        mainStage.dispose();
        pauseStage.dispose();
        chooseMissilePlayer1Stage.dispose();
        chooseMissilePlayer2Stage.dispose();
    }
}