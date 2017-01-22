package com.rightovers.wave.player;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.rightovers.wave.Main;
import com.rightovers.wave.map.Environment;
import com.rightovers.wave.screens.EndMenu;
import com.rightovers.wave.screens.GameScreen;
import com.rightovers.wave.utils.Funcs;
import com.rightovers.wave.utils.IResourceable;
import com.rightovers.wave.utils.Loader;
import com.rightovers.wave.utils.SoundManager;


public class Player implements IResourceable {

    public boolean releaseInertia;
    private float timeSinceLastUpdatedInertia;
    // it will be set to true when you crash
    public boolean dying = false;

    public static Player getInstance() {
        if (Main.getInstance().instances.get(Player.class) == null) {
            Main.getInstance().instances.put(Player.class, new Player());
        }
        return (Player) Main.getInstance().instances.get(Player.class);
    }

    @Override
    public void loadAssets() {
        Loader.getInstance().addAsset(Main.getInstance().assetsGroupName, this.waveGraphics.WAVE_PACK_NAME, Loader.AssetType.TEXTURE_ATLAS);
        Loader.getInstance().addAsset(Main.getInstance().assetsGroupName, "images/poseidon.pack", Loader.AssetType.TEXTURE_ATLAS);

    }

    private enum DIRECTION {
        UP,
        DOWN
    }

    private final float MAX_SPEED = 200;
    private final float MIN_SPEED = 50;

    public Controller controller;
    public WaveGraphics waveGraphics;
    public PoseidonGraphics poseidonGraphics;
    public PlayerPhysics physics;

    public final float INITIAL_SPEED = 3;
    private float speed = this.INITIAL_SPEED;
    public float speedMultiplier = 1f;
    public final float SPEED_SLOWDOWN_MULTIPLIER = .5f;

    public float getSpeed() {
        return this.speed * this.speedMultiplier;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public final float WHIP_ANIMATION_MULTIPLIER = 4f;
    public final float WAVE_ANIMATION_MULTIPLIER = 2f;
    public final int INITIAL_INERTIA = 5;
    public final int MAX_INERTIA = 40;

    public final int INERTIA_INCREMENT_STEP = 10;
    public final int INERTIA_DECREMENT_STEP_SLOW = 7;
    public final int INERTIA_DECREMENT_STEP_FAST = 19;

    private int inertiaDecrementStep = this.INERTIA_DECREMENT_STEP_SLOW;

    public int inertia = this.INITIAL_INERTIA;

    public int getInertiaDecrementStep() {
        return this.inertiaDecrementStep;
    }

    public void setInertiaDecrementStep(int inertiaSlowdownSpeed) {
        this.inertiaDecrementStep = inertiaSlowdownSpeed;
    }


    private int getInertia() {
        return this.inertia;
    }

    public void setInertia(int inertia) {
        this.inertia = inertia;
        if (this.inertia < this.INITIAL_INERTIA) {
            this.inertia = this.INITIAL_INERTIA;
        }
        if (this.inertia > this.MAX_INERTIA) {
            this.inertia = this.MAX_INERTIA;
        }
    }


    public float getDistance() {
        return this.distance;
    }

    private float distance;

    public void create() {
        this.controller = new Controller();
        this.waveGraphics = new WaveGraphics();
        this.poseidonGraphics = new PoseidonGraphics();
        this.physics = new PlayerPhysics();

    }


    public void update(float delta) {
        this.physics.update(delta);
        this.waveGraphics.update(delta);
        this.poseidonGraphics.update(delta);
        this.controller.update(delta);

        this.distance += this.getSpeed() * Environment.getInstance().getScaleRatio() * delta;

        if (this.timeSinceLastUpdatedInertia > 1f) {
            Player.getInstance().setInertia(Player.getInstance().inertia - this.inertiaDecrementStep);
            this.timeSinceLastUpdatedInertia = 0;
        }
        if (this.speed < this.INITIAL_SPEED) {
            this.speed += delta;
        }
        this.timeSinceLastUpdatedInertia += delta;
    }

    public void incrementInertia() {
        this.setInertia(this.inertia + this.INERTIA_INCREMENT_STEP);
    }

    public void whip() {
        SoundManager.getInstance().whip();
        this.poseidonGraphics.whip();
        this.waveGraphics.whip();
    }

    public float getStrength() {
        int frame = this.waveGraphics.getAbsoluteKeyFrame();

        // frames 1-40 = down
        if (frame > 1 && frame <= 40) {
            return 0;
        }
        else if (frame > 40 && frame <= 65) {
            return Funcs.changeRange(40, 65, 0, 1, frame);
        }
        else if (frame > 65 && frame <= 80) {
            return (1f - Funcs.changeRange(65, 80, 0, 1, frame));
        }
        return 0;
    }

    public void die() {
        float endScreenAfter = 2;
        // mark as dying
        this.dying = true;
        // kill the speed
        this.speed = 0;

        // camera shake
        //GameScreen.getInstance().startCameraShake(20, endScreenAfter * 1000);

        // TODO throw poseidon
        // TODO stop wave animation


        Main.getInstance().stage.addAction(Actions.sequence(Actions.delay(endScreenAfter), new Action() {
            @Override
            public boolean act(float delta) {
                GameScreen.getInstance().resetAfterCameraShake(Main.getInstance().camera);
                Funcs.setScreen(EndMenu.getInstance());
                return true;
            }
        }));
    }
}
