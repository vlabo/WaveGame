package com.rightovers.wave.player;

import com.rightovers.wave.Main;
import com.rightovers.wave.utils.IResourceable;


public class Player implements IResourceable {

    public static Player getInstance() {
        if (Main.getInstance().instances.get(Player.class) == null) {
            Main.getInstance().instances.put(Player.class, new Player());
        }
        return (Player) Main.getInstance().instances.get(Player.class);
    }

    @Override
    public void loadAssets() {

    }

    private enum DIRECTION {
        UP,
        DOWN
    }

    private final float maxSpeed = 200;
    private final float minSpeed = 100;

    public Controller controller;
    public PlayerGraphics playerGraphics;
    public PlayerPhysics physics;
    public float speed = 100;

    public float getDistance() {
        return this.distance;
    }

    private float distance;

    public DIRECTION direction;

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
    }

    public void accelerate() {
        if (this.speed < this.maxSpeed) {
            this.speed += 10;
        }
    }

    public void deccelerate() {
        if (this.speed > this.minSpeed) {
            this.speed -= 1;
        }
    }

}
