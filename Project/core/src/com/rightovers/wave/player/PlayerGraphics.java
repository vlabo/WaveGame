package com.rightovers.wave.player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rightovers.wave.Main;
import com.rightovers.wave.utils.Funcs;
import com.rightovers.wave.utils.Loader;

public class PlayerGraphics {

    public static final String WAVE_PACK_NAME = "images/wave.atlas";
    public Animation<TextureRegion> waveAnimation;

    float stateTime;

    public PlayerGraphics() {
        this.waveAnimation = new Animation<TextureRegion>(1 / 70f, Loader.getInstance().getTextureAtlas(this.WAVE_PACK_NAME).getRegions(), Animation.PlayMode.LOOP);

    }

    public void draw(float delta) {
        Main.getInstance().batch.draw(this.waveAnimation.getKeyFrame(this.stateTime), 0, 0, Funcs.percentWidth(60), Funcs.percentWidth(40));
    }

    public void update(float delta) {
        this.stateTime += delta;

        float fps = (1 / 75f) / ((Player.getInstance().speed / 300) * 0.5f);
        Funcs.print(fps + " " + Player.getInstance().speed);

        this.waveAnimation.setFrameDuration(fps);

    }


}
