package com.rightovers.wave.player;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rightovers.wave.Main;
import com.rightovers.wave.utils.Funcs;
import com.rightovers.wave.utils.Loader;

public class PlayerGraphics {

    public static final String WAVE_PACK_NAME = "images/wave.atlas";
    public WaveAnimation<TextureRegion> waveAnimation;

    float stateTime;
    private float lastInertia;

    public PlayerGraphics() {
        this.waveAnimation = new WaveAnimation<TextureRegion>(1 / 25f, Loader.getInstance().getTextureAtlas(this.WAVE_PACK_NAME).getRegions(), WaveAnimation.PlayMode.LOOP_PINGPONG);

    }

    public void draw(float delta) {
        if(lastInertia != 0) {
            Main.getInstance().batch.draw(this.waveAnimation.getKeyFrame(this.stateTime), 0, 0, Funcs.percentWidth(60), Funcs.percentWidth(40));
        }
    }

    public void update(float delta) {
        this.stateTime += delta;

        float fps = (1 / 75f) / ((Player.getInstance().speed / 300) * 0.5f);
        Funcs.print(fps + " " + Player.getInstance().speed);

        this.waveAnimation.setFrameDuration(fps);

        int inertia = Player.getInstance().inertia;
        if(inertia != lastInertia){
            int midPoint = waveAnimation.getAllFramesCount()/2;

            if(inertia<=midPoint) {
                this.waveAnimation.setLeftLoopFrameNumber((midPoint - inertia));
                this.waveAnimation.setRightLoopFrameNumber((midPoint + inertia));
                this.waveAnimation.calculateTrimmedKeyFrames();
            }
        }
        lastInertia = Player.getInstance().inertia;

    }


}
