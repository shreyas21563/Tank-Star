package com.mygdx.game.ProgressBars;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.io.Serializable;

public class fuelBar extends ProgressBar implements Serializable {
    public fuelBar(int width, int height) {
        super(0f, 1f, 0.01f, false, new ProgressBarStyle());
        Pixmap pixmap = new Pixmap(0, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLUE);
        pixmap.fill();
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        getStyle().knob = drawable;
        getStyle().knobBefore = new TextureRegionDrawable(new Texture("barFilled.png"));
        setWidth(width);
        setHeight(height);
        setAnimateDuration(1f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
