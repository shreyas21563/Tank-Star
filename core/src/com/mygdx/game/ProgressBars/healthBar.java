package com.mygdx.game.ProgressBars;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.io.Serializable;

public class healthBar extends ProgressBar implements Serializable {
    private ProgressBarStyle style;
    private Pixmap getPixmap(int width, int height, Color color){
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        return pixmap;
    }
    private void disposer(Pixmap pixmap){
        pixmap.dispose();
    }
    private void disposer(Pixmap pixmap1, Pixmap pixmap2){
        pixmap2.dispose();
        pixmap1.dispose();
    }
    public healthBar(int width, int height){
        super(0, 1, 0.01f, false, new ProgressBarStyle());
        style = getStyle();
        Pixmap pixmapRed = getPixmap(width, height, Color.RED);
        style.background = new TextureRegionDrawable(new Texture(pixmapRed));
        disposer(pixmapRed);
        Pixmap pixmapGreen1 = getPixmap(width, height, Color.PURPLE);
        Pixmap pixmapGreen2 = getPixmap(0, height, Color.PURPLE);
        style.knobBefore = new TextureRegionDrawable(new Texture(pixmapGreen1));
        style.knob = new TextureRegionDrawable(new Texture(pixmapGreen2));
        disposer(pixmapGreen1, pixmapGreen2);

        super.setWidth(width);
        super.setHeight(height);
        setAnimateDuration(1f);
        setValue(1f);
    }
}
