package com.rightovers.wave.player;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rightovers.wave.Main;
import com.rightovers.wave.utils.Funcs;
import com.rightovers.wave.utils.Loader;

public class PoseidonGraphics {

    public Animation<TextureRegion> animation;
    private float stepTime = 0;
    private float sizeWidth = Funcs.percentWidth(28);
    private float ratio = 1.86f;
    private Float[] yPositions;
    private float currentY = 0;
    private float speedUpAnimationTime;
    private float lastX = Funcs.percentWidth(4);
    private float lastRotation = 0;


    public void whip() {
        this.speedUpAnimationTime = 0.4f;
    }

    public PoseidonGraphics() {
        this.animation = new Animation<TextureRegion>(1 / 50f, Loader.getInstance().getTextureAtlas("images/poseidon.pack").getRegions(), Animation.PlayMode.LOOP);

        this.yPositions = new Float[80];
        fillYPositions();
    }

    public void draw(float delta) {
        // get the Y position for the frame for the
        float targetYPos = this.yPositions[Player.getInstance().waveGraphics.getAbsoluteKeyFrame()];

        // first time
        if (this.currentY == 0) {
            this.currentY = targetYPos;
        }

        if (this.currentY < targetYPos) {
            this.currentY += delta * (targetYPos - this.currentY) * 6;
        }
        else {
            this.currentY -= delta * (this.currentY - targetYPos) * 12;
        }

        // normal
        if (Player.getInstance().dying == false) {
            Main.getInstance().batch.draw(this.animation.getKeyFrame(this.stepTime), this.lastX, Funcs.percentHeight(this.currentY), this.sizeWidth, this.sizeWidth / this.ratio);
        }
        // player is dying - throw him back
        else {
            this.lastX -= delta * 100;
            this.lastRotation += delta * 50;

            Main.getInstance().batch.draw(this.animation.getKeyFrame(this.stepTime), this.lastX, Funcs.percentHeight(this.currentY), 0, 0, this.sizeWidth, this.sizeWidth / this.ratio, 1, 1, this.lastRotation);
        }

    }

    public void update(float delta) {

        if (this.speedUpAnimationTime > 0f) {
            this.stepTime += delta * Player.getInstance().WHIP_ANIMATION_MULTIPLIER;
            this.speedUpAnimationTime -= delta;

        }
        else {
            this.stepTime += delta;
        }
    }

    private void fillYPositions() {
        for (int i = 0; i <= 14; i++) {
            this.yPositions[i] = 33f;
        }
        for (int i = 15; i <= 28; i++) {
            this.yPositions[i] = this.yPositions[i - 1] - 2;
        }
        for (int i = 29; i <= 40; i++) {
            this.yPositions[i] = 7f;
        }
        for (int i = 41; i <= 50; i++) {
            this.yPositions[i] = 6f;
        }
        for (int i = 51; i <= 62; i++) {
            this.yPositions[i] = 5f;
        }
        for (int i = 63; i <= 79; i++) {
            this.yPositions[i] = this.yPositions[i - 1] + 2.2f;
        }

    }
}
