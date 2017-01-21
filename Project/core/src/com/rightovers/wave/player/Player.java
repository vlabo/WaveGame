package com.rightovers.wave.player;

import com.rightovers.wave.Main;
import com.rightovers.wave.utils.IResourceable;
import com.rightovers.wave.utils.Loader;


public class Player implements IResourceable {

    public boolean releaseInertia;
    private float timeSinceLastUpdatedInertia;

    public static Player getInstance() {
        if (Main.getInstance().instances.get(Player.class) == null) {
            Main.getInstance().instances.put(Player.class, new Player());
        }
        return (Player) Main.getInstance().instances.get(Player.class);
    }

    @Override
    public void loadAssets() {
        Loader.getInstance().addAsset(Main.getInstance().assetsGroupName, this.waveGraphics.WAVE_PACK_NAME, Loader.AssetType.TEXTURE_ATLAS);
    }

    private enum DIRECTION {
        UP,
        DOWN
    }

    private final float MAX_SPEED = 700;
    private final float MIN_SPEED = 200;

    public Controller controller;
    public WaveGraphics waveGraphics;
    public PlayerPhysics physics;
    public float speed = 200;


    public int defaultInertia = 5;
    public int maxInertia = 40;
    public int inertiaIncrementStep = 5;
    public int inertia = this.defaultInertia;

    private int getInertia() {
        return this.inertia;
    }

    public void setInertia(int inertia) {
        this.inertia = inertia;
        if (this.inertia < this.defaultInertia) {
            this.inertia = this.defaultInertia;
        }
        if (this.inertia > this.maxInertia) {
            this.inertia = this.maxInertia;
        }
    }


    public float getDistance() {
        return this.distance;
    }

    private float distance;

    public void create() {
        this.controller = new Controller();
        this.waveGraphics = new WaveGraphics();
        this.physics = new PlayerPhysics();

    }


    public void update(float delta) {
        this.physics.update(delta);
        this.waveGraphics.update(delta);
        this.controller.update(delta);

        this.distance += this.speed * delta;

        if (this.timeSinceLastUpdatedInertia > 5.0f) {
            //Player.getInstance().setInertia(Player.getInstance().inertia - Player.getInstance().inertiaIncrementStep);
            this.timeSinceLastUpdatedInertia = 0;
        }
        this.timeSinceLastUpdatedInertia += delta;
    }

    public void incrementInertia() {
        this.setInertia(this.inertia + this.inertiaIncrementStep);
    }

    public void releaseInertia() {
        this.releaseInertia = true;
    }

    public DIRECTION getDirection() {
        int frame = this.waveGraphics.waveAnimation.getKeyFrameIndex(this.waveGraphics.stateTime);

        // frames 1-40 = down
        if (frame > 1 && frame <= 40) {
            return DIRECTION.DOWN;
        }
        else {
            return DIRECTION.UP;
        }
    }

}
