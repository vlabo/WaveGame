package com.rightovers.wave.player;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rightovers.wave.Main;
import com.rightovers.wave.utils.Loader;

public class PoseidonGraphics {

    public Animation<TextureRegion> animation;
    private float stepTime = 0;

    public PoseidonGraphics() {
        this.animation = new Animation<TextureRegion>(1 / 50f, Loader.getInstance().getTextureAtlas("images/poseidon.pack").getRegions(), Animation.PlayMode.LOOP);
    }

    public void draw(float delta) {
        Main.getInstance().batch.draw(this.animation.getKeyFrame(this.stepTime), 0, 0, 200, 200);
    }

    public void update(float delta) {
        this.stepTime += delta;
    }
}
