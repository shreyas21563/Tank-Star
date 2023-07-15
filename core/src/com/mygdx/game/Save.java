package com.mygdx.game;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.ProgressBars.fuelBar;
import com.mygdx.game.ProgressBars.healthBar;
import com.mygdx.game.Sprites.Balls;
import com.mygdx.game.Sprites.Tanks;

import java.io.Serializable;
import java.util.ArrayList;

public class Save implements Serializable {
    public float FuelBar1Value, FuelBar2Value;
    public float HealthBar1Value, HealthBar2Value;
    public boolean turnPlayer1;
    public float X1, Y1, Angle1;
    public float X2, Y2, Angle2;
    public int Num1, Num2;
    public Save(float FuelBar1, float FuelBar2, float HealthBar1, float HealthBar2, boolean turnPlayer1, float X1, float Y1, float Angle1, float X2, float Y2, float Angle2, int Num1, int Num2){
        this.FuelBar1Value = FuelBar1;
        this.FuelBar2Value = FuelBar2;
        this.HealthBar1Value = HealthBar1;
        this.HealthBar2Value = HealthBar2;
        this.turnPlayer1 = turnPlayer1;
        this.X1 = X1;
        this.Y1 = Y1;
        this.Angle1 = Angle1;
        this.X2 = X2;
        this.Y2 = Y2;
        this.Angle2 = Angle2;
        this.Num1 = Num1;
        this.Num2 = Num2;
    }
}
