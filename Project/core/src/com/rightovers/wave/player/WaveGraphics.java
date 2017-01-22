package com.rightovers.wave.player;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rightovers.wave.Main;
import com.rightovers.wave.utils.Funcs;
import com.rightovers.wave.utils.Loader;

public class WaveGraphics {

    public static final String WAVE_PACK_NAME = "images/wave.atlas";
    private float waveFps;
    public WaveAnimation<TextureRegion> waveAnimation;

    float stateTime;
    private int lastInertia;
    private float speedUpAnimationTime;


    public WaveGraphics() {
        this.waveFps = 1 / 30f;
        this.waveAnimation = new WaveAnimation<TextureRegion>(this.waveFps, Loader.getInstance().getTextureAtlas(this.WAVE_PACK_NAME).getRegions(), WaveAnimation.PlayMode.LOOP_PINGPONG);
        this.waveAnimation.setFrameDuration(this.waveFps);
        updateWaveAnimation();
    }

    public void draw(float delta) {
        if (this.lastInertia != 0) {
            Main.getInstance().batch.draw(this.waveAnimation.getKeyFrame(this.stateTime), 0, 0, Funcs.percentWidth(60), Funcs.percentWidth(40));
        }
    }

    public void update(float delta) {

        if(speedUpAnimationTime > 0f){
            this.stateTime += delta*Player.getInstance().WAVE_ANIMATION_MULTIPLIER;
            speedUpAnimationTime -= delta;

        }
        else{
            this.stateTime += delta;
        }
        int currentAbsoluteFrameKey = getAbsoluteKeyFrame();

        if (Player.getInstance().inertia != this.lastInertia) {
            if (currentAbsoluteFrameKey > ((this.waveAnimation.getAllFramesCount() / 2) - Player.getInstance().inertia) && currentAbsoluteFrameKey < ((this.waveAnimation.getAllFramesCount() / 2) + Player.getInstance().inertia)

                    ) {
                updateWaveAnimation();

                int currentRelativeFrameKey = currentAbsoluteFrameKey - this.waveAnimation.getLeftLoopFrameNumber();

                this.stateTime = currentRelativeFrameKey * this.waveFps;

                if (this.waveAnimation.loopingForward == false) {
                    this.stateTime += this.waveFps * ((this.waveAnimation.getTrimmedFramesCount() - currentRelativeFrameKey) * 2);
                }

                this.lastInertia = Player.getInstance().inertia;
            }
        }


    }

    private void updateWaveAnimation() {
        int midPoint = this.waveAnimation.getAllFramesCount() / 2;
        this.waveAnimation.setLeftLoopFrameNumber((midPoint - Player.getInstance().inertia));
        this.waveAnimation.setRightLoopFrameNumber((midPoint + Player.getInstance().inertia));

        this.waveAnimation.calculateTrimmedKeyFrames();
    }

    public int getAbsoluteKeyFrame() {
        return this.waveAnimation.getLeftLoopFrameNumber() + this.waveAnimation.getKeyFrameIndex(this.stateTime);
    }

    public void whip() {
        speedUpAnimationTime = 0.4f;
    }
}
