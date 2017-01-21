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
        Loader.getInstance().addAsset(Main.getInstance().assetsGroupName, this.playerGraphics.WAVE_PACK_NAME, Loader.AssetType.TEXTURE_ATLAS);
    }

    private enum DIRECTION {
        UP,
        DOWN
    }

    private final float MAX_SPEED = 700;
    private final float MIN_SPEED = 200;

    public Controller controller;
    public PlayerGraphics playerGraphics;
    public PlayerPhysics physics;
    public float speed = 200;




    public int defaultInertia = 5;
    public int maxInertia = 40;
    public int inertiaIncrementStep = 5;
    public int inertia = defaultInertia;
    private int getInertia() {
        return inertia;
    }

    public void setInertia(int inertia) {
        this.inertia = inertia;
        if(this.inertia < defaultInertia)
            this.inertia = defaultInertia;
        if(this.inertia > maxInertia)
            this.inertia = maxInertia;
    }



    public float getDistance() {
        return this.distance;
    }

    private float distance;

    public void create() {
        this.controller = new Controller();
        this.playerGraphics = new PlayerGraphics();
        this.physics = new PlayerPhysics();

    }


    public void update(float delta) {
        this.physics.update(delta);
        this.playerGraphics.update(delta);
        this.controller.update(delta);

        this.distance += this.speed * delta;

        if(this.timeSinceLastUpdatedInertia > 5.0f){
            //Player.getInstance().setInertia(Player.getInstance().inertia - Player.getInstance().inertiaIncrementStep);
            this.timeSinceLastUpdatedInertia=0;
        }
        this.timeSinceLastUpdatedInertia += delta;
    }

    public void incrementInertia() {
        this.inertia += this.inertiaIncrementStep;
    }

    public void releaseInertia() {
        this.releaseInertia = true;
    }

    public DIRECTION getDirection() {
        int frame = this.playerGraphics.waveAnimation.getKeyFrameIndex(this.playerGraphics.stateTime);

        // frames 1-40 = down
        if (frame > 1 && frame <= 40) {
            return DIRECTION.DOWN;
        }
        else {
            return DIRECTION.UP;
        }
    }

}
