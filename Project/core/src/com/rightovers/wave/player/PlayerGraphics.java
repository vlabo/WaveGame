package com.rightovers.wave.player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rightovers.wave.Main;
import com.rightovers.wave.utils.Funcs;
import com.rightovers.wave.utils.Loader;

public class PlayerGraphics {

    public static final String WAVE_PACK_NAME = "images/wave.atlas";
    Animation<TextureRegion> runningAnimation;

    float stateTime;

    public PlayerGraphics() {
        this.runningAnimation = new Animation<TextureRegion>(0.033f, Loader.getInstance().getTextureAtlas(this.WAVE_PACK_NAME).getRegions(), Animation.PlayMode.LOOP);

    }

    public void draw(float delta) {
        Main.getInstance().batch.draw(Loader.getInstance().getTexture("images/ground.png"), Player.getInstance().physics.getBox2DBody().getPosition().x - Player.getInstance().getDistance(), Player.getInstance().physics.getBox2DBody().getPosition().y, 500, 500);

        Main.getInstance().batch.draw(this.runningAnimation.getKeyFrame(this.stateTime), 0, 0, Funcs.percentWidth(60), Funcs.percentWidth(40));

    }

    public void update(float delta) {
        this.stateTime += delta;
    }


}
