package com.rightovers.wave.player;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rightovers.wave.Main;
import com.rightovers.wave.utils.Funcs;
import com.rightovers.wave.utils.Loader;

public class WaveGraphics {

    public static final String WAVE_PACK_NAME = "images/wave.atlas";
    private final float waveFps;
    public WaveAnimation<TextureRegion> waveAnimation;

    float stateTime;
    private int lastInertia;



    public WaveGraphics() {
        waveFps = 1 / 25f;
        this.waveAnimation = new WaveAnimation<TextureRegion>(waveFps, Loader.getInstance().getTextureAtlas(this.WAVE_PACK_NAME).getRegions(), WaveAnimation.PlayMode.LOOP_PINGPONG);
        updateWaveAnimation();
    }

    public void draw(float delta) {
        if(lastInertia != 0) {
            Main.getInstance().batch.draw(this.waveAnimation.getKeyFrame(this.stateTime), 0, 0, Funcs.percentWidth(60), Funcs.percentWidth(40));
        }
    }

    public void update(float delta) {
        this.stateTime += delta;

        float fps = (1 / (25f+Player.getInstance().inertia));

        this.waveAnimation.setFrameDuration(fps);


        int currentAbsoluteFrameKey = this.waveAnimation.getLeftLoopFrameNumber()+waveAnimation.getKeyFrameIndex(this.stateTime);

        if(Player.getInstance().inertia != lastInertia) {
            if (Player.getInstance().releaseInertia == false) {
                updateWaveAnimation();

                int currentRelativeFrameKey = currentAbsoluteFrameKey-this.waveAnimation.getLeftLoopFrameNumber();

                this.stateTime =currentRelativeFrameKey* waveFps;

                if(this.waveAnimation.loopingForward == false){
                    this.stateTime += waveFps*((this.waveAnimation.getTrimmedFramesCount()-currentRelativeFrameKey)*2);
                }

            }
        }
        lastInertia = Player.getInstance().inertia;



    }
    private void updateWaveAnimation(){
        int midPoint = waveAnimation.getAllFramesCount() / 2;
        this.waveAnimation.setLeftLoopFrameNumber((midPoint - Player.getInstance().inertia));
        this.waveAnimation.setRightLoopFrameNumber((midPoint + Player.getInstance().inertia));
        Funcs.print(midPoint - Player.getInstance().inertia);
        this.waveAnimation.calculateTrimmedKeyFrames();
    }

}
